package com.sms.notifications.textmessages.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sms.notifications.textmessages.common.model.SMSMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestLambdaHandler implements RequestHandler<SQSEvent, Void> {

    private static final Logger logger = LoggerFactory.getLogger(RequestLambdaHandler.class);

    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        for (SQSMessage msg : event.getRecords()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                SMSMessage message = objectMapper.readValue(msg.getBody(), SMSMessage.class);
                logger.info("New SQS message arrived.");
                logger.info("Phone number: " + message.getPhoneNumber());
                logger.info("Content: " + message.getContent());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
