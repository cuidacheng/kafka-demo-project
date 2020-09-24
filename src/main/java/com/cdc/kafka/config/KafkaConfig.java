package com.cdc.kafka.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * kafka配置相关信息
 *
 * @author: cuidc
 * @date: 2020-09-21 16:11
 */

@Component
public class KafkaConfig {
    @Value("${zookeeper.server.url:localhost:9092}")
    private String bootstrapServers;
    @Value("${zookeeper.acks:1}")
    private String acks;
    @Value("${zookeeper.retries:0}")
    private String retries;
    @Value("${zookeeper.batch.size:16384}")
    private String batchSize;
    @Value("${zookeeper.linger.ms:1}")
    private String lingerMs;
    @Value("${zookeeper.buffer.memory:33554432}")
    private String bufferMemory;
    @Value("${zookeeper.key.serializer:org.apache.kafka.common.serialization.StringSerializer}")
    private String keySerializer;
    @Value("${zookeeper.value.serializer:org.apache.kafka.common.serialization.StringSerializer}")
    private String valueSerializer;
    @Value("${zookeeper.key.deserializer:org.apache.kafka.common.serialization.StringDeserializer}")
    private String keyDeserializer;
    @Value("${zookeeper.value.deserializer:org.apache.kafka.common.serialization.StringDeserializer}")
    private String valueDeserializer;

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public String getAcks() {
        return acks;
    }

    public void setAcks(String acks) {
        this.acks = acks;
    }

    public String getRetries() {
        return retries;
    }

    public void setRetries(String retries) {
        this.retries = retries;
    }

    public String getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(String batchSize) {
        this.batchSize = batchSize;
    }

    public String getLingerMs() {
        return lingerMs;
    }

    public void setLingerMs(String lingerMs) {
        this.lingerMs = lingerMs;
    }

    public String getBufferMemory() {
        return bufferMemory;
    }

    public void setBufferMemory(String bufferMemory) {
        this.bufferMemory = bufferMemory;
    }

    public String getKeySerializer() {
        return keySerializer;
    }

    public void setKeySerializer(String keySerializer) {
        this.keySerializer = keySerializer;
    }

    public String getValueSerializer() {
        return valueSerializer;
    }

    public void setValueSerializer(String valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

    public String getKeyDeserializer() {
        return keyDeserializer;
    }

    public void setKeyDeserializer(String keyDeserializer) {
        this.keyDeserializer = keyDeserializer;
    }

    public String getValueDeserializer() {
        return valueDeserializer;
    }

    public void setValueDeserializer(String valueDeserializer) {
        this.valueDeserializer = valueDeserializer;
    }
}
