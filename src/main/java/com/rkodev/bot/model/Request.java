package com.rkodev.bot.model;

import java.util.List;

public class Request {
    private String text;
    private List<RequestAction> actions;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<RequestAction> getActions() {
        return actions;
    }

    public void setActions(List<RequestAction> actions) {
        this.actions = actions;
    }
}
