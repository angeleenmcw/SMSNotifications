package com.sms.notifications.textmessages.notification.service.impl;

import com.amazonaws.services.sns.model.AmazonSNSException;
import com.amazonaws.services.sns.model.InvalidParameterException;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sms.notifications.textmessages.client.service.IRestService;
import com.sms.notifications.textmessages.common.exception.CustomErrorCode;
import com.sms.notifications.textmessages.config.sns.utility.SnsUtility;
import com.sms.notifications.textmessages.config.sqs.utility.SqsUtility;
import com.sms.notifications.textmessages.common.model.SMSMessage;
import com.sms.notifications.textmessages.common.model.SQSMessage;
import com.sms.notifications.textmessages.email.model.Mail;
import com.sms.notifications.textmessages.email.service.IEmailService;
import com.sms.notifications.textmessages.notification.manager.INotificationCRUDManager;
import com.sms.notifications.textmessages.notification.model.entity.Notification;
import com.sms.notifications.textmessages.notification.service.INotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class NotificationService implements INotificationService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private SnsUtility snsUtility;

    @Autowired
    private SqsUtility sqsUtility;

    @Autowired
    private INotificationCRUDManager notificationCRUDManager;

    @Autowired
    private IEmailService emailService;

    @Autowired
    private IRestService restService;

    @Value("${aws.sqs.queue.standard.name}")
    private String standardQueueName;

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Override
    public void sendSMSAndSQSMessages(SMSMessage message) {

        try {
            PublishResult publishResult = snsUtility.sendSMSMessage(message);
            if (publishResult.getSdkHttpMetadata().getHttpStatusCode() == 200) {
                try {
                    String body = objectMapper.writeValueAsString(message);
                    SQSMessage sqsMessage = new SQSMessage();
                    sqsMessage.setName(standardQueueName);
                    sqsMessage.setBody(body);

                    SendMessageResult sendMessageResult = sqsUtility.sendSqsMessage(sqsMessage);
                    if (sendMessageResult.getSdkHttpMetadata().getHttpStatusCode() == 200) {
//                        SMSMessage response = this.restService.exchange("https://stratodemo.blockapps.net", HttpMethod.GET, SMSMessage.class, "token");

                        Notification notification = new Notification();
                        notification.setMessageID(sendMessageResult.getMessageId());
                        notification.setPhone(message.getPhoneNumber());
                        notification.setEmail(message.getEmail());
                        notification.setMessage(message.getContent());
                        notification.setCreatedDate(new Date());
                        notification = this.notificationCRUDManager.save(notification);

                        Mail mail = new Mail();
                        mail.setTo(message.getEmail());
                        mail.setSubject("Beanstalk Notification");
                        mail.setContent(message.getContent());
                        try {
                            emailService.sendSimpleEmail(mail);
                        } catch (MailException e) {
                            logger.info("Email sending failed.");
                        }
                    }

                } catch (JsonProcessingException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CustomErrorCode.INVALID_REQUEST_ERROR.name());
                } catch (InvalidParameterException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CustomErrorCode.QUEUE_URL_INVALID_ERROR.name());
                } catch (AmazonSQSException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CustomErrorCode.SEND_SQS_MESSAGE_ERROR.name());
                }

            }

        } catch (InvalidParameterException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CustomErrorCode.PHONE_NUMBER_INVALID_ERROR.name());
        } catch (AmazonSNSException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CustomErrorCode.SEND_SMS_MESSAGE_ERROR.name());
        }

    }

    @Override
    public List<Notification> getNotifications() {
        List<Notification> messages = notificationCRUDManager.findAll();
        return messages;
    }

}
