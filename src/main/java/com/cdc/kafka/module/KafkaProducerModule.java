package com.cdc.kafka.module;

import com.cdc.kafka.config.KafkaConfig;
import com.cdc.kafka.entity.TopicMessageEntity;
import kafka.utils.Json;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * {阐述类的作用}
 *
 * @author: cuidc
 * @date: 2020-09-21 00:13
 */

@Component
public class KafkaProducerModule {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerModule.class);

    private ThreadPoolExecutor threadPoolExecutor;
    private Properties properties;
    private ProducerRecord<String, String> producerRecord;

    @Autowired
    private Environment environment;
    @Autowired
    private KafkaConfig kafkaConfig;

    @PostConstruct
    void initMethod() {
        properties = new Properties();
        properties.setProperty(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
        properties.setProperty("acks", kafkaConfig.getAcks());
        properties.setProperty(CommonClientConfigs.RETRIES_CONFIG, kafkaConfig.getRetries());
        properties.setProperty("batch.size", kafkaConfig.getBatchSize());
        properties.setProperty("linger.ms", kafkaConfig.getLingerMs());
        properties.setProperty("buffer.memory", kafkaConfig.getBufferMemory());
        properties.setProperty("key.serializer", kafkaConfig.getKeySerializer());
        properties.setProperty("value.serializer", kafkaConfig.getValueSerializer());

        int corePoolSize = !StringUtils.isEmpty(environment.getProperty("kafkaProducer.thread.corePoolSize")) ?
                Integer.parseInt(environment.getProperty("kafkaProducer.thread.corePoolSize")) : 2;
        int maxPoolSize = !StringUtils.isEmpty(environment.getProperty("kafkaProducer.thread.maximumPoolSize")) ?
                Integer.parseInt(environment.getProperty("kafkaProducer.thread.maximumPoolSize")) : 4;
        // 10分钟
        long keepAliveTime = !StringUtils.isEmpty(environment.getProperty("kafkaProducer.thread.keepAliveTime")) ?
                Long.parseLong(environment.getProperty("kafkaProducer.thread.keepAliveTime")) : 10 * 60;
        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
    }

    /**
     * 向kafka发送消息
     * @param topicName
     * @param message
     * @return
     */
    public void sendMessage(String topicName, String message) {
        TopicMessageEntity topicMessageEntity = new TopicMessageEntity();
        topicMessageEntity.setTopicName(topicName);
        topicMessageEntity.setMessage(message);
        logger.info("KafkaProducerModule::sendMessage topicName = {}, message = {}", topicName, message);
        //  threadPoolExecutor.execute(new KafkaProducerThread(topicMessageEntity));
        threadPoolExecutor.execute(() -> {
            // 发送消息
            Producer<String, String> producer = new KafkaProducer<>(properties);
            producerRecord = new ProducerRecord<>(topicName, message);
            long startTime = System.currentTimeMillis();
            // acks = 0 的情况下不需要callback校验
            if ("0".equals(properties.getProperty("acks"))) {
                producer.send(producerRecord);
                logger.info("KafkaProducerModule::sendMessage send message success, topicName = {}, message = {}",
                        topicName, message);
            } else {
                producer.send(producerRecord, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception exception) {
                        if (metadata != null) {
                            long endTime = System.currentTimeMillis();
                            logger.info("KafkaProducerModule::sendMessage callback success, topicName = {}, " +
                                    "message = {}，responseTIme = {}", topicName, message, endTime - startTime);
                        } else {
                            logger.error("KafkaProducerModule::sendMessage callback fail, topicName = {}, message = {}, " +
                                    "exceptionMsg = {}", topicName, message, exception.getMessage());
                        }
                    }
                });
            }
        });
    }
}
