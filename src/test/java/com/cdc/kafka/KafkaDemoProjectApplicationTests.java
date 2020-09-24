package com.cdc.kafka;

import com.cdc.kafka.constant.KafkaErrorEnumConstant;
import com.cdc.kafka.entity.ResultInfo;
import com.cdc.kafka.entity.TopicEntity;
import com.cdc.kafka.exception.KafkaException;
import com.cdc.kafka.service.KafkaConsumerService;
import com.cdc.kafka.service.KafkaProducerService;
import com.cdc.kafka.service.KafkaTopicService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

@SpringBootTest
class KafkaDemoProjectApplicationTests {
	private static final Logger logger = LoggerFactory.getLogger(KafkaDemoProjectApplicationTests.class);

	@Autowired
	private KafkaTopicService kafkaTopicService;
	@Autowired
	private KafkaConsumerService consumerService;
	@Autowired
	private KafkaProducerService producerService;

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

	@Test
	void receiveMessageTest() {
		String topicName = "test1";
		String clazz = "com.cdc.kafka.service.impl.KafkaConsumerServiceImpl";
		String method = "receiveMessage";

		// 先生产消息
		for (int i = 0; i < 100; i++) {
			producerService.sendTopicMessage(topicName, "message-" + i);
		}
		// 接受消息
		ResultInfo resultInfo = consumerService.receiveTopicMessage(method, clazz, topicName);
		if (KafkaErrorEnumConstant.SUCCESS.getErrorCode().equals(resultInfo.getErrorCode())) {
			logger.info("接受消息触发成功");
		} else {
			logger.info("接受消息触发失败， message = {}", resultInfo.getErrorMsg());
		}
		try {
			Thread.sleep(2000 * 20);
		} catch (Exception e) {

		}
	}

}
