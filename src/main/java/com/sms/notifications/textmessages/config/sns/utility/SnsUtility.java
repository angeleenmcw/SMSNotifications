package com.sms.notifications.textmessages.config.sns.utility;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.sms.notifications.textmessages.common.model.SMSMessage;

import java.util.HashMap;
import java.util.Map;

public class SnsUtility {

    private AmazonSNS snsClient;

    public SnsUtility(String region, String accessKey, String secretKey) {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        snsClient = AmazonSNSClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    public PublishResult sendSMSMessage(SMSMessage message) {
        Map<String, MessageAttributeValue> smsAttributes =
                new HashMap<>();
        smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
                .withStringValue("Promotional") //Sets the type to promotional.
                .withDataType("String"));
        return snsClient.publish(new PublishRequest()
                .withSubject("SMS Notification")
                .withMessage(message.getContent())
                .withPhoneNumber(message.getPhoneNumber())
                .withMessageAttributes(smsAttributes));
    }

}
