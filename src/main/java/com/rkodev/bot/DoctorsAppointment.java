package com.rkodev.bot;

import com.codepoetics.protonpack.collectors.CompletableFutures;
import com.microsoft.bot.builder.*;
import com.microsoft.bot.schema.Activity;
import com.microsoft.bot.schema.ChannelAccount;
import com.rkodev.bot.domain.AppointmentService;
import com.rkodev.bot.model.Appointment;
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

    private static final String WELCOME_MESSAGE = "Welcome to Pacifist Appointment Bot, how may I help you today?";

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
                .map(channel -> turnContext.sendActivity(
                        MessageFactory.text(WELCOME_MESSAGE)))
                .collect(CompletableFutures.toFutureList())
                .thenApply(resourceResponses -> null);
    }

    @Override
    protected CompletableFuture<Void> onMessageActivity(TurnContext turnContext) {

        String turnText = turnContext.getActivity().getText();
        return turnContext
                .sendActivity(processAppointment(null, turnText))
                .thenApply(sendResult -> null);
    }

    private Activity processAppointment(Appointment appointment, String turnText) {
        return MessageFactory.text("Echo: " + turnText);
    }
}
