package com.rkodev.bot.appstate;

import com.microsoft.bot.schema.Activity;
import com.rkodev.bot.domain.AppointmentService;
import com.rkodev.bot.model.Appointment;

/**
 * The app can handle only one state at a time
 */
public interface AppState {
    Activity handleRequest(AppointmentService appointmentService, Appointment appointment, String response);
}
