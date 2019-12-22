package com.sms.notifications.textmessages.config.sqs;

import com.sms.notifications.textmessages.config.sqs.utility.SqsUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SqsConfig {

    @Value("${aws.region}")
    private String region;
    @Value("${aws.accessKey}")
    private String accessKey;
    @Value("${aws.secretKey}")
    private String secretKey;
    @Value("${aws.sqs.queue.base.url}")
    private String baseQueueUrl;

    @Bean
    public SqsUtility sqsUtility() {
        return new SqsUtility(region, accessKey, secretKey, baseQueueUrl);
    }

}
