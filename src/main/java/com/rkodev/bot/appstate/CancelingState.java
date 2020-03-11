package com.rkodev.bot.appstate;

import com.microsoft.bot.schema.Activity;
import com.rkodev.bot.domain.AppointmentService;
import com.rkodev.bot.model.Appointment;
import com.rkodev.bot.utils.MessageUtils;

public class CancelingState implements AppState {

    @Override
    public Activity handleRequest(AppointmentService appointmentService, Appointment appointment, String response) {
        appointment.reset();
        Appointment result = appointmentService.getAppointment(response);

        if (result == null) {
            String message = "Appointment " + response + " does not exist. How else may I assist?";
            return MessageUtils.getIntroMessage(message);
        }
        appointmentService.cancelAppointment(response);

        String message = "Appointment # " + response + " has been cancelled. Anything else?";
        return MessageUtils.getIntroMessage(message);
    }
}
