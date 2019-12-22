package com.sms.notifications.textmessages.config.sns;

import com.sms.notifications.textmessages.config.sns.utility.SnsUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SnsConfig {

    @Value("${aws.region}")
    private String region;
    @Value("${aws.accessKey}")
    private String accessKey;
    @Value("${aws.secretKey}")
    private String secretKey;

    @Bean
    public SnsUtility snsUtility(){
        return new SnsUtility(region, accessKey, secretKey);
    }

}
