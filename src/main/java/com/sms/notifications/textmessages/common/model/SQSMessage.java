package com.sms.notifications.textmessages.common.model;

public class SQSMessage {

    private String name;

    private String body;

    public SQSMessage() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
