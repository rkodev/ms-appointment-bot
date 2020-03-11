package com.rkodev.bot.domain;

import com.rkodev.bot.model.Appointment;
import com.rkodev.bot.model.ProcessResult;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.List;

public interface AppointmentService {

    /***
     * creates a new appointment
     * @param appointment
     * @return
     */
    ProcessResult<Appointment> bookAppointment(@Nullable Appointment appointment);

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

    /**
     * updates the appointment and returns the details
     *
     * @param appointment
     * @return
     */
    ProcessResult<Appointment> postpone(Appointment appointment);

    /**
     * Returns a list of dates that have an appointment slot
     * next to the one requested by the user
     *
     * @param currentSlot
     * @return
     */
    List<Date> getNextAvailableSlots(String currentSlot);

}
