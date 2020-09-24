package com.cdc.kafka.service;

import com.cdc.kafka.entity.ResultInfo;

/**
 * kafka生产消息
 *
 * @author cuidc
 * @date 2020-09-12
 */
public interface KafkaProducerService {

    /**
     * 向topic发送消息
     *
     * @param topicName
     * @param message
     * @return
     */
    ResultInfo sendTopicMessage(String topicName, String message);
}
