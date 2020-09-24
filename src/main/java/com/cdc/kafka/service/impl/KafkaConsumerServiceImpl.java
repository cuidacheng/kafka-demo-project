package com.cdc.kafka.service.impl;

import com.cdc.kafka.constant.KafkaErrorEnumConstant;
import com.cdc.kafka.entity.ResultInfo;
import com.cdc.kafka.exception.KafkaException;
import com.cdc.kafka.module.KafkaConsumeModule;
import com.cdc.kafka.service.KafkaConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * kafka消费者实现
 *
 * @author cuidc
 * @date 2020-09-12
 */
@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerServiceImpl.class);

    @Autowired
    private ResultInfo resultInfo;
    @Autowired
    private KafkaConsumeModule consumeModule;

    @Override
    public ResultInfo receiveTopicMessage(String method, String clazz, String topicName) {
        resultInfo.setResultInfo(KafkaErrorEnumConstant.SUCCESS);
        try {
            consumeModule.receiveTopicMessage(method, clazz, topicName);
        } catch (KafkaException e) {
            resultInfo.setResultInfo(e);
        }
        return resultInfo;
    }

    public void receiveMessage(StringBuilder messageBuild) {
        logger.info("KafkaConsumerServiceImpl::receiveMessage message = {}", messageBuild.toString());
    }

    @Override
    public void receiveTopicMessage(String topicName) {
        String clazz = "com.cdc.kafka.service.impl.KafkaConsumerServiceImpl";
        String method = "receiveMessage";
        try {
            consumeModule.receiveTopicMessage(method, clazz, topicName);
        } catch (KafkaException e) {

        }
    }
}
