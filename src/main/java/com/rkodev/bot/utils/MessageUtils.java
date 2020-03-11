package com.rkodev.bot.utils;

import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.schema.ActionTypes;
import com.microsoft.bot.schema.Activity;
import com.microsoft.bot.schema.CardAction;
import com.microsoft.bot.schema.SuggestedActions;
import com.rkodev.bot.domain.AppointmentType;

import java.util.ArrayList;
import java.util.List;

public class MessageUtils {

    public static Activity getIntroMessage(String message) {
        // welcome message

        Activity welcome = MessageFactory.text(message);

        List<CardAction> cardActions = new ArrayList<>();
        for (AppointmentType appointmentType : AppointmentType.values()) {
            CardAction cardAction = new CardAction();
            cardAction.setText(appointmentType.toString());
            cardAction.setValue(appointmentType.toString());
            cardAction.setType(ActionTypes.IM_BACK);
            cardActions.add(cardAction);
        }

        welcome.setSuggestedActions(new SuggestedActions() {{
            setActions(cardActions);
        }});

        return welcome;
    }
}
