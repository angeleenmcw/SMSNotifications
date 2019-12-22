package com.sms.notifications.textmessages.common.response;

public class CoreResponse {

    private Object result;
    private String message;

    public CoreResponse(Object result) {
        this.result = result;
    }

    public CoreResponse(String message) {
        this.message = message;
    }

    public CoreResponse(Object result, String message) {
        this.result = result;
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
