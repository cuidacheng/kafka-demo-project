package com.cdc.kafka.vo;

/**
 * 发送消息请求
 *
 * @author: cuidc
 * @date: 2020-09-23 23:43
 */

public class SendMessageRequestVO {
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

    @Override
    public String toString() {
        return "SendMessageRequestVO{" +
                "topicName='" + topicName + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
