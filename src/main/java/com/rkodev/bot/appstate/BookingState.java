package com.rkodev.bot.appstate;

import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.schema.Activity;
import com.rkodev.bot.domain.AppointmentService;
import com.rkodev.bot.model.Appointment;
import com.rkodev.bot.model.ProcessResult;
import com.rkodev.bot.utils.MessageUtils;

public class BookingState implements AppState {

    @Override
    public Activity handleRequest(AppointmentService appointmentService, Appointment appointment, String response) {
        ProcessResult<Appointment> bookAppointment = appointmentService.bookAppointment(appointment, response);

        if (bookAppointment.getResult() == ProcessResult.ResultState.COMPLETE) {
            String message = bookAppointment.getResponseText() + ". How else may I assist?";
            return MessageUtils.getIntroMessage(message);
        }

        return MessageFactory.text(bookAppointment.getResponseText());
    }
}
