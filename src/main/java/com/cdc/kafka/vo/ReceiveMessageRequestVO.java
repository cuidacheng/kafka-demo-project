package com.cdc.kafka.vo;

/**
 * {阐述类的作用}
 *
 * @author: cuidc
 * @date: 2020-09-23 23:46
 */

public class ReceiveMessageRequestVO {

    private String topicName;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    @Override
    public String toString() {
        return "ReceiveMessageRequestVO{" +
                "topicName='" + topicName + '\'' +
                '}';
    }
}
