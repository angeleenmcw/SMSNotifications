package com.sms.notifications.textmessages.notification.controller;

import com.amazonaws.services.sqs.model.Message;
import com.sms.notifications.textmessages.common.response.CoreResponse;
import com.sms.notifications.textmessages.common.model.SMSMessage;
import com.sms.notifications.textmessages.notification.model.entity.Notification;
import com.sms.notifications.textmessages.notification.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class NotificationController {

    @Autowired
    private INotificationService notificationService;

    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Notification>> getSQSMessages() {
        List<Notification> messages = notificationService.getNotifications();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<CoreResponse> sendSMSAndSQSMessages(@Valid @RequestBody SMSMessage message) {
        notificationService.sendSMSAndSQSMessages(message);
        return ResponseEntity.ok(new CoreResponse("Send SMS and SQS messages successfully."));
    }

}
