package com.sms.notifications.textmessages.common.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SMSMessage {

    @NotBlank(message = "Phone number is mandatory.")
    @Size(min = 6, message = "Phone number must be longer or equal than 6 characters.")
    private String phoneNumber;

    @NotBlank(message = "Email is mandatory.")
    @Email(message = "Invalid email.")
    private String email;

    @NotBlank(message = "Content is mandatory.")
    private String content;

    public SMSMessage() {}

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
