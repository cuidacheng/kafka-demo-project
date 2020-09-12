package com.cdc.kafka.entity;

import java.io.Serializable;

/**
 * kafka主题操作请求
 *
 * @author cuidc
 * @date 2020-09-10
 */
public class TopicEntity implements Serializable {
    /**
     * 主题名称
     */
    private String topicName;
    /**
     * 分区数量
     */
    private Integer partition = 1;
    /**
     * 副本数量
     */
    private Short replication = 1;
    /**
     * topic响应方式 0 1 all
     */
    private String acks;
    /**
     * 数据满足一定大小后才会被发生 默认是16KB
     */
    private Long batchSize = 16 * 1024L;
    /**
     * 缓存，默认32M
     * 消息都是先进入到客户端本地的内存缓冲里，然后把很多消息收集成一个一个的Batch，再发送到Broker上去,提供性能
     */
    private Long bufferMemory = 33554432L;
    /**
     * 消息滞留时间， 设置默认是5s
     * 消息一旦写入一个Batch，最多等待这么多时间，他一定会跟着Batch一起发送出去
     */
    private Long lingerMs = 5 * 1000L;
    /**
     * 重试次数， 默认为0
     */
    private Integer retries = 0;
    /**
     * 主题描述
     */
    private String describe;

    public String getTopicName() {
        return topicName;
    }

    public TopicEntity setTopicName(String topicName) {
        this.topicName = topicName;
        return this;
    }

    public Integer getPartition() {
        return partition;
    }

    public TopicEntity setPartition(Integer partition) {
        this.partition = partition;
        return this;
    }

    public Short getReplication() {
        return replication;
    }

    public TopicEntity setReplication(Short replication) {
        this.replication = replication;
        return this;
    }

    public String getDescribe() {
        return describe;
    }

    public TopicEntity setDescribe(String describe) {
        this.describe = describe;
        return this;
    }
    public String getAcks() {
        return acks;
    }

    public void setAcks(String acks) {
        this.acks = acks;
    }

    public Long getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Long batchSize) {
        this.batchSize = batchSize;
    }

    public Long getBufferMemory() {
        return bufferMemory;
    }

    public void setBufferMemory(Long bufferMemory) {
        this.bufferMemory = bufferMemory;
    }

    public Long getLingerMs() {
        return lingerMs;
    }

    public void setLingerMs(Long lingerMs) {
        this.lingerMs = lingerMs;
    }

    public Integer getRetries() {
        return retries;
    }

    public void setRetries(Integer retries) {
        this.retries = retries;
    }

  @Override
  public String toString() {
    return "TopicEntity{"
        + "topicName='"
        + topicName
        + '\''
        + ", partition="
        + partition
        + ", replication="
        + replication
        + ", acks='"
        + acks
        + '\''
        + ", batchSize="
        + batchSize
        + ", bufferMemory="
        + bufferMemory
        + ", lingerMs="
        + lingerMs
        + ", retries="
        + retries
        + ", describe='"
        + describe
        + '\''
        + '}';
  }
}
