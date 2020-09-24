package com.cdc.kafka.thread;

import com.cdc.kafka.entity.TopicMessageEntity;

/**
 * 发送消息处理线程
 *
 * @author: cuidc
 * @date: 2020-09-21 15:11
 */

public class KafkaProducerThread extends Thread {
    private TopicMessageEntity topicMessageEntity;

    public KafkaProducerThread(TopicMessageEntity topicMessageEntity) {
        this.topicMessageEntity = topicMessageEntity;
    }

    /**
     * 具体的kafka发送消息
     */
    @Override
    public void run() {

    }
}
