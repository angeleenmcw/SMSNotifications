package com.sms.notifications.textmessages.notification.manager;

import com.sms.notifications.textmessages.notification.model.entity.Notification;

import java.util.List;

public interface INotificationCRUDManager {

    List<Notification> findAll();

    Notification findById(String notificationID);

    Notification findByPhone(String phone);

    Notification save(Notification body);

}
