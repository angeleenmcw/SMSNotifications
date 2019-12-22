package com.sms.notifications.textmessages.notification.manager.impl;

import com.sms.notifications.textmessages.notification.manager.INotificationCRUDManager;
import com.sms.notifications.textmessages.notification.model.entity.Notification;
import com.sms.notifications.textmessages.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationCRUDManager implements INotificationCRUDManager {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public List<Notification> findAll() {
        Iterable<Notification> iterable = notificationRepository.findAll();
        List<Notification> notifications = new ArrayList<>();
        iterable.forEach(notifications::add);
        return notifications;
    }

    @Override
    public Notification findById(String id) {
        Notification notification = notificationRepository.findById(id).get();
        return notification;
    }

    @Override
    public Notification findByPhone(String phone) {
        Notification notification = notificationRepository.findByPhone(phone);
        return notification;
    }

    @Override
    public Notification save(Notification body) {
        body = notificationRepository.save(body);
        return body;
    }

}
