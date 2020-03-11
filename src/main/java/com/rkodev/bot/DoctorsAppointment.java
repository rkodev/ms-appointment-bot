package com.rkodev.bot;

import com.codepoetics.protonpack.collectors.CompletableFutures;
import com.microsoft.bot.builder.*;
import com.microsoft.bot.schema.Activity;
import com.microsoft.bot.schema.ChannelAccount;
import com.rkodev.bot.appstate.*;
import com.rkodev.bot.domain.AppointmentService;
import com.rkodev.bot.model.Appointment;
import com.rkodev.bot.utils.MessageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Provides the Spring implementation from the application.
 * <p>
 * The application needs to remember state and conversation history
 */
@Component
public class DoctorsAppointment extends ActivityHandler {

    private ConversationState conversationState;
    private UserState userState;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    public DoctorsAppointment(ConversationState withConversationState, UserState withUserState) {
        conversationState = withConversationState;
        userState = withUserState;
    }

    /**
     * pass state onTurn
     *
     * @param turnContext
     * @return
     */
    @Override
    public CompletableFuture<Void> onTurn(TurnContext turnContext) {
        return super.onTurn(turnContext)
                .thenCompose(turnResult -> conversationState.saveChanges(turnContext))
                .thenCompose(saveResult -> userState.saveChanges(turnContext));
    }

    /***
     * Welcome message when login in
     * @param membersAdded
     * @param turnContext
     * @return
     */
    @Override
    protected CompletableFuture<Void> onMembersAdded(List<ChannelAccount> membersAdded, TurnContext turnContext) {
        return membersAdded.stream()
                .filter(member -> !StringUtils.equals(member.getId(), turnContext.getActivity().getRecipient().getId()))
                .map(channel -> turnContext.sendActivity(MessageUtils.getIntroMessage("Welcome to Pacifist Appointment Bot, What can I help you with today?")))
                .collect(CompletableFutures.toFutureList())
                .thenApply(resourceResponses -> null);
    }

    @Override
    protected CompletableFuture<Void> onMessageActivity(TurnContext turnContext) {

        // appointment state
        StatePropertyAccessor<Appointment> dataAccessor = conversationState.createProperty("data");
        CompletableFuture<Appointment> dataFuture = dataAccessor.get(turnContext, Appointment::new);

        String turnText = turnContext.getActivity().getText();

        return dataFuture.thenApply(thisAppointment ->
                turnContext.sendActivity(processAppointment(thisAppointment, turnText)))
                .thenApply(resourceResponse -> null);
    }

    private Activity processAppointment(Appointment appointment, String turnText) {
        AppState appState = null;
        switch (appointment.getExpectedResponse()) {
            case SELECT_SERVICE:
                appState = new SelectServiceState();
                break;
            case BOOKING_SERVICE_NAME:
            case BOOKING_SERVICE_PHONE:
            case BOOKING_SERVICE_DAY:
                appState = new BookingState();
                break;
            case CHECK_SERVICE_NUMBER:
                appState = new CheckState();
                break;
            case CANCEL_SERVICE_NUMBER:
                appState = new CancelingState();
                break;
            default:
                break;
        }

        if (appState != null)
            return appState.handleRequest(appointmentService, appointment, turnText);


        return MessageUtils.getIntroMessage("Sorry, I don't understand that request please choose from below");
    }


}
