package com.rkodev.bot.domain;

import com.rkodev.bot.model.Appointment;
import com.rkodev.bot.model.ExpectedResponse;
import com.rkodev.bot.model.ProcessResult;
import com.rkodev.bot.model.SavedAppointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AppointmentProvider implements AppointmentService {

    @Autowired
    private SavedAppointment appointments;

    private AtomicInteger appointmentNum = new AtomicInteger(0);

    @Override
    public ProcessResult<Appointment> bookAppointment(Appointment appointment, String response) {
        ProcessResult<Appointment> processResult = new ProcessResult<>(appointment);

        switch (appointment.getExpectedResponse()) {
            case BOOKING_SERVICE_NAME:
                appointment.setClient(response);
                appointment.setExpectedResponse(ExpectedResponse.BOOKING_SERVICE_PHONE);
                processResult.setResponseText("What's your phone number?");
                processResult.setResult(ProcessResult.ResultState.NEW_REQUEST);
                break;
            case BOOKING_SERVICE_PHONE:
                appointment.setPhoneNumber(response);
                appointment.setExpectedResponse(ExpectedResponse.BOOKING_SERVICE_DAY);
                processResult.setResponseText("When will you like to see the doctor?");
                processResult.setResult(ProcessResult.ResultState.NEW_REQUEST);
                break;
            case BOOKING_SERVICE_DAY:
                appointment.setDay(response);
                saveAppointment(appointment);
                appointment.setExpectedResponse(ExpectedResponse.SELECT_SERVICE);
                processResult.setResponseText("Your service has been booked, You can use this number " + appointment.getNumber() + " for reference.");
                processResult.setResult(ProcessResult.ResultState.COMPLETE);
                break;
            default:
                break;
        }

        return processResult;
    }

    /**
     * persist to a form of storage
     *
     * @param appointment
     */
    private void saveAppointment(Appointment appointment) {
        appointment.setNumber("#" + appointmentNum.incrementAndGet());
        appointments.put(appointment.getNumber(), appointment);
    }

    @Override
    public Appointment getAppointment(String appointmentNumber) {
        return appointments.get(appointmentNumber);
    }

    @Override
    public ProcessResult<Appointment> cancelAppointment(String appointmentNumber) {
        Appointment appointment = appointments.get(appointmentNumber);
        ProcessResult<Appointment> processResult = new ProcessResult<>(appointment);
        if (appointment != null) {
            appointments.remove(appointmentNumber);
            processResult.setResponseText("Appointment has been deleted");
            processResult.setResult(ProcessResult.ResultState.COMPLETE);
        } else {
            processResult.setResponseText("Appointment does not exist");
            processResult.setResult(ProcessResult.ResultState.ERROR);
        }
        return processResult;
    }

}
