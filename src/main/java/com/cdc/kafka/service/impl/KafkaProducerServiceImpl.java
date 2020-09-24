package com.cdc.kafka.service.impl;

import com.cdc.kafka.constant.KafkaErrorEnumConstant;
import com.cdc.kafka.entity.ResultInfo;
import com.cdc.kafka.exception.KafkaException;
import com.cdc.kafka.module.KafkaConsumeModule;
import com.cdc.kafka.module.KafkaProducerModule;
import com.cdc.kafka.service.KafkaProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * kafka生产消息实现
 *
 * @author cuidc
 * @date 2020-09-12
 */

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerServiceImpl.class);

    @Autowired
    private KafkaProducerModule producerModule;
    @Autowired
    private KafkaConsumeModule consumeModule;
    @Autowired
    private ResultInfo resultInfo;

    @Override
    public ResultInfo sendTopicMessage(String topicName, String message){
        resultInfo.setResultInfo(KafkaErrorEnumConstant.SUCCESS);
        if (StringUtils.isEmpty(topicName)) {
            resultInfo.setResultInfo(KafkaErrorEnumConstant.SEND_MESSAGE_TOPIC_EMPTY);
            return resultInfo;
        }
        if (StringUtils.isEmpty(message)) {
            resultInfo.setResultInfo(KafkaErrorEnumConstant.SEND_MESSAGE_MESSAGE_EMPTY);
            return resultInfo;
        }
        producerModule.sendMessage(topicName, message);
        return resultInfo;
    }
}
