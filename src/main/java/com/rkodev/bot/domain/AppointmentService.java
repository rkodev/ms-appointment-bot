package com.rkodev.bot.domain;

import com.rkodev.bot.model.Appointment;
import com.rkodev.bot.model.ProcessResult;
import org.springframework.lang.Nullable;

public interface AppointmentService {

    /***
     * creates a new appointment
     * @param appointment
     * @return
     */
    ProcessResult<Appointment> bookAppointment(@Nullable Appointment appointment, String response);

    /***
     * returns by number
     * @param appointmentNumber
     * @return
     */
    Appointment getAppointment(String appointmentNumber);

    /***
     * cancel by appointmentByNumber
     * @param appointmentNumber
     * @return
     */
    ProcessResult<Appointment> cancelAppointment(String appointmentNumber);

}
