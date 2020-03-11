package com.rkodev.bot.appstate;

import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.schema.Activity;
import com.rkodev.bot.domain.AppointmentService;
import com.rkodev.bot.domain.AppointmentType;
import com.rkodev.bot.model.Appointment;
import com.rkodev.bot.model.ExpectedResponse;
import com.rkodev.bot.utils.MessageUtils;

public class SelectServiceState implements AppState {
    @Override
    public Activity handleRequest(AppointmentService appointmentService, Appointment appointment, String response) {
        AppointmentType appointmentType = AppointmentType.fromString(response);
        if (appointmentType == null) {
            appointment.setExpectedResponse(ExpectedResponse.SELECT_SERVICE);
            return MessageUtils.getIntroMessage("Sorry, I don't understand that request please choose an option below");
        }

        switch (appointmentType) {
            case BOOK_APPOINTMENT:
                appointment.setExpectedResponse(ExpectedResponse.BOOKING_SERVICE_NAME);
                return MessageFactory.text("I'd like some details about you and so I can fill in your appointment, Lets start with your name");
            case CANCEL_APPOINTMENT:
                appointment.setExpectedResponse(ExpectedResponse.CANCEL_SERVICE_NUMBER);
                return MessageFactory.text("Kindly enter your appointment number to confirm");
            case CHECK_APPOINTMENT:
                appointment.setExpectedResponse(ExpectedResponse.CHECK_SERVICE_NUMBER);
                return MessageFactory.text("Kindly enter your appointment number to confirm");
            default:
                return MessageUtils.getIntroMessage("Sorry, I don't understand that request please choose from here");
        }
    }
}
