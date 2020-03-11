package com.rkodev.bot.domain;


import org.springframework.lang.Nullable;

/***
 * Recognizable actions
 */
public enum AppointmentType {

    BOOK_APPOINTMENT("Book appointment"),
    CHECK_APPOINTMENT("Check appointment"),
    CANCEL_APPOINTMENT("Cancel appointment");

    private String actionType;

    AppointmentType(String actionType) {
        this.actionType = actionType;
    }

    @Override
    public String toString() {
        return actionType;
    }

    @Nullable
    public static AppointmentType fromString(String value) {
        AppointmentType[] items = values();
        for (AppointmentType appointmentType : items) {
            if (appointmentType.toString().equalsIgnoreCase(value)) {
                return appointmentType;
            }
        }
        return null;
    }
}
