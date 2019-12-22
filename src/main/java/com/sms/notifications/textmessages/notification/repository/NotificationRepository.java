package com.sms.notifications.textmessages.notification.repository;

import com.sms.notifications.textmessages.notification.model.entity.Notification;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBPagingAndSortingRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;

@EnableScan
public interface NotificationRepository extends DynamoDBPagingAndSortingRepository<Notification, String> {

    Notification findByPhone(String phone);

}
