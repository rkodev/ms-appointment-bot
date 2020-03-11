package com.rkodev.bot.model;

public class Appointment {

    private String number;
    private String client;
    private String phoneNumber;
    private String day;
    private ExpectedResponse expectedResponse = ExpectedResponse.SELECT_SERVICE;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public ExpectedResponse getExpectedResponse() {
        return expectedResponse;
    }

    public void setExpectedResponse(ExpectedResponse expectedResponse) {
        this.expectedResponse = expectedResponse;
    }

    public void reset() {
        number = null;
        client = null;
        phoneNumber = null;
        day = null;
        expectedResponse = ExpectedResponse.SELECT_SERVICE;
    }

    public String getSummary() {
        return "Number: " + getNullOrEmpty(number) + ", \n" +
                "Name: " + getNullOrEmpty(client) + ", \n" +
                "Day: " + getNullOrEmpty(day) + ", \n" +
                "Phone Number: " + getNullOrEmpty(phoneNumber) + ", \n";
    }

    private String getNullOrEmpty(String value) {
        return value == null ? "" : value;

    }
}
