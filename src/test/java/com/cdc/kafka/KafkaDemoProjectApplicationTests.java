package com.cdc.kafka;

import com.cdc.kafka.entity.TopicEntity;
import com.cdc.kafka.exception.KafkaException;
import com.cdc.kafka.service.KafkaTopicService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class KafkaDemoProjectApplicationTests {
	private static final Logger logger = LoggerFactory.getLogger(KafkaDemoProjectApplicationTests.class);

	@Autowired
	private KafkaTopicService kafkaTopicService;

	@Test
	void topicCreateTest() {
		TopicEntity topicEntity = new TopicEntity();
		topicEntity.setTopicName("test1");
		topicEntity.setPartition(3);
		try{
			kafkaTopicService.createTopic(topicEntity);
		} catch (KafkaException e) {
			logger.error(e.getErrorCode() + e.getErrorMsg());
		}
	}

	@Test
	void topicDeleteTest() {
		List<String> topicListToBeDelete = Arrays.asList("test1");
		try{
			kafkaTopicService.deleteTopic(topicListToBeDelete);
		} catch (KafkaException e) {
			logger.error(e.getErrorCode() + e.getErrorMsg());
		}
	}

}
