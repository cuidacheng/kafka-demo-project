package com.cdc.kafka.service;

import com.cdc.kafka.entity.TopicEntity;
import com.cdc.kafka.exception.KafkaException;

import java.util.List;

/**
 * kafka主题相关动态操作接口
 *
 * @author cuidc
 */
public interface KafkaTopicService {
    /**
     * 动态创建kafka的topic
     *
     * @param topicEntity
     * @return
     * @throws KafkaException
     */
    void createTopic(TopicEntity topicEntity) throws KafkaException;

    /**
     * 获取kafka的topic名称
     *
     * @return
     * @throws KafkaException
     */
    List<String> listTopic() throws KafkaException;

    /**
     * 删除topic
     *
     * @param topicNames
     * @throws KafkaException
     */
    void deleteTopic(List<String> topicNames) throws KafkaException;
}
