package com.sms.notifications.textmessages.config.dynamodb;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.ContainerCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.sms.notifications.textmessages.notification.model.entity.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@Component
@Configuration
@EnableDynamoDBRepositories(basePackages = {"com.sms.notifications.textmessages"})
public class DynamoDBConfig {

    @Value("${aws.dynamodb.endpoint}")
    private String dynamoDBEndpoint;

    @Value("${aws.accesskey}")
    private String accessKey;

    @Value("${aws.secretkey}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    private static final Logger logger = LoggerFactory.getLogger(DynamoDBConfig.class);

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonDynamoDBClientBuilder clientBuilder = AmazonDynamoDBClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials));

        AmazonDynamoDB amazonDynamoDB;
        if (StringUtils.isEmpty(dynamoDBEndpoint)) {
            amazonDynamoDB = clientBuilder
                    .withRegion(region)
                    .build();
        } else {
            amazonDynamoDB =  AmazonDynamoDBClientBuilder.standard()
                    .withRegion(region)
                    .withCredentials(new ContainerCredentialsProvider()).build();
        }

        this.initializeTables(amazonDynamoDB,
                Arrays.asList(
                        Notification.class
                )
        );

        return amazonDynamoDB;
    }

    private void initializeTables(AmazonDynamoDB amazonDynamoDB, List<Class> modelClasses ) {
        try{
            DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

            for (Class cls : modelClasses) {
                logger.info("creating table: " + cls.getSimpleName());
                CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(cls);
                tableRequest.setProvisionedThroughput(new ProvisionedThroughput(10L, 10L));

                boolean created = TableUtils.createTableIfNotExists(amazonDynamoDB, tableRequest);

                if (created) {
                    logger.info("created table: " + cls.getSimpleName());
                } else {
                    logger.info("table already exists:" + cls.getSimpleName());
                }
            }

            ListTablesResult tablesResult = amazonDynamoDB.listTables();

            logger.info("current tables : ");
            for (String name : tablesResult.getTableNames()) {
                logger.info("\t name: " + name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
