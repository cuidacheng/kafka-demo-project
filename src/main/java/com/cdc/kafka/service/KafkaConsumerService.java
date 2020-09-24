package com.cdc.kafka.service;

import com.cdc.kafka.entity.ResultInfo;

/**
 * kafka消费者
 *
 * @author cuidc
 * @date 2020-09-12
 */
public interface KafkaConsumerService {

    /**
     * 接受topic的信息
     *
     * @param method
     * @param clazz
     * @param topicName
     * @return
     */
    ResultInfo receiveTopicMessage(String method, String clazz, String topicName);

    /**
     * 接受消息
     * @param topicName
     */
    void receiveTopicMessage(String topicName);
}
