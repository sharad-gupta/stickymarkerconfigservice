package com.shgupta.stickymarker.service.urlnote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

@Configuration
public class UrlNoteConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(UrlNoteConfig.class);

	@Bean
	public AmazonDynamoDB amazonDynamoDB() {
		LOGGER.info("amazonDynamoDB ==> invoked test init1 >>>");
		AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard().build();
		return amazonDynamoDB;
	}

	@Bean
	public DynamoDB dynamoDB(AmazonDynamoDB amazonDynamoDB) {
		LOGGER.info("dynamoDB ==> invoked v2");
		return new DynamoDB(amazonDynamoDB);
	}

	@Bean
	public DynamoDBMapper dynamoDbMapper(AmazonDynamoDB amazonDynamoDB) {
		LOGGER.info("dynamoDbMapper ==> invoked");
		return new DynamoDBMapper(amazonDynamoDB);
	}
}
