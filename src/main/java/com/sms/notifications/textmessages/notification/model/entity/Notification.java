package com.sms.notifications.textmessages.notification.model.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Date;
import java.util.Objects;

@DynamoDBTable(tableName="Notification")
public class Notification {

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    private String id;

    @DynamoDBAttribute
    private String messageID;

    @DynamoDBAttribute
    private String phone;

    @DynamoDBAttribute
    private String email;

    @DynamoDBAttribute
    private String message;

    @DynamoDBAttribute
    private String smartContractID;

    @DynamoDBAttribute
    private Date createdDate;

    public Notification() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSmartContractID() {
        return smartContractID;
    }

    public void setSmartContractID(String smartContractID) {
        this.smartContractID = smartContractID;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification notification = (Notification) o;
        return Objects.equals(id, notification.id) &&
                Objects.equals(messageID, notification.messageID) &&
                Objects.equals(phone, notification.phone) &&
                Objects.equals(email, notification.email) &&
                Objects.equals(message, notification.message) &&
                Objects.equals(smartContractID, notification.smartContractID) &&
                Objects.equals(createdDate, notification.createdDate)
                ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, messageID, phone, email, message, smartContractID, createdDate);
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", messageID='" + messageID + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", message='" + message + '\'' +
                ", smartContractID='" + smartContractID + '\'' +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }

}