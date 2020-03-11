package com.rkodev.bot.domain;

import com.rkodev.bot.model.Appointment;
import com.rkodev.bot.model.ProcessResult;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AppointmentProvider implements AppointmentService {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

    @Override
    public ProcessResult<Appointment> bookAppointment(Appointment appointment) {
        return null;
    }

    @Override
    public Appointment getAppointment(String appointmentNumber) {
        return null;
    }

    @Override
    public ProcessResult<Appointment> cancelAppointment(String appointmentNumber) {
        return null;
    }

    @Override
    public ProcessResult<Appointment> postpone(Appointment appointment) {
        return null;
    }

    @Override
    public List<Date> getNextAvailableSlots(String currentSlot) {
        return null;
    }
}
