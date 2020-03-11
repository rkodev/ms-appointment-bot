package com.rkodev.bot.model;

public class ProcessResult<T> {

    public enum ResultState {
        COMPLETE, NEW_REQUEST, ERROR
    }

    private T payLoad;
    private String responseText;
    private ResultState result;

    public ProcessResult(T payLoad) {
        this.payLoad = payLoad;
    }

    public T getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(T payLoad) {
        this.payLoad = payLoad;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public ResultState getResult() {
        return result;
    }

    public void setResult(ResultState result) {
        this.result = result;
    }
}
