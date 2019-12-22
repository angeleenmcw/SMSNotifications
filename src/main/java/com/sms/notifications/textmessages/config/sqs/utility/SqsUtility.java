package com.sms.notifications.textmessages.config.sqs.utility;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.*;
import com.sms.notifications.textmessages.common.model.SQSMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqsUtility {

    private AmazonSQS sqsClient;
    private String baseQueueUrl;

    public SqsUtility(String region, String accessKey, String secretKey, String baseQueueUrl) {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        sqsClient = AmazonSQSClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();

        this.baseQueueUrl = baseQueueUrl;
    }

    public SendMessageResult sendSqsMessage(SQSMessage message) {
        Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
        messageAttributes.put("name", new MessageAttributeValue()
                .withStringValue(message.getName())
                .withDataType("String"));

        SendMessageRequest sendMessageStandardQueue = new SendMessageRequest()
                .withQueueUrl(baseQueueUrl + message.getName())
                .withMessageBody(message.getBody())
                .withDelaySeconds(0)
                .withMessageAttributes(messageAttributes);

        SendMessageResult result = sqsClient.sendMessage(sendMessageStandardQueue);
        return result;
    }

    public List<Message> getSqsMessages(String queueName) {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(baseQueueUrl + queueName)
                .withWaitTimeSeconds(10)
                .withMaxNumberOfMessages(10);

        List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).getMessages();
        return messages;
    }

    public DeleteMessageResult deleteMessage(Message message, String queueName) {
        DeleteMessageResult result = sqsClient.deleteMessage(new DeleteMessageRequest()
                .withQueueUrl(baseQueueUrl + queueName)
                .withReceiptHandle(message.getReceiptHandle()));
        return result;
    }

}
