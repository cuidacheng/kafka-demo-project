package com.cdc.kafka.entity;

/**
 * Kafka生产与消费信息体
 *
 * @author: cuidc
 * @date: 2020-09-21 10:11
 */

public class TopicMessageEntity {
    private String topicName;
    private String message;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
