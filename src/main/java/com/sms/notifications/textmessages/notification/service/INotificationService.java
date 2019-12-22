package com.sms.notifications.textmessages.notification.service;

import com.sms.notifications.textmessages.common.model.SMSMessage;
import com.sms.notifications.textmessages.notification.model.entity.Notification;

import java.util.List;

public interface INotificationService {

    public void sendSMSAndSQSMessages(SMSMessage message);

    public List<Notification> getNotifications();

}
