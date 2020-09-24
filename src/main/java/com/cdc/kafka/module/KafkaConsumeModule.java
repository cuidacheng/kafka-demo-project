package com.cdc.kafka.module;

import com.cdc.kafka.config.KafkaConfig;
import com.cdc.kafka.constant.KafkaErrorEnumConstant;
import com.cdc.kafka.exception.KafkaException;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

/**
 * 消费topic消息
 *
 * @author: cuidc
 * @date: 2020-09-21 20:07
 */

@Component
public class KafkaConsumeModule {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumeModule.class);
    private Properties properties;

    @Autowired
    private KafkaConfig kafkaConfig;

    @PostConstruct
    void initMethod() {
        properties = new Properties();
        properties.setProperty(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
        properties.setProperty(CommonClientConfigs.GROUP_ID_CONFIG, "org.apache.kafka");
        properties.setProperty("enable.auto.commit", "true");
        properties.setProperty("auto.commit.interval.ms", "1000");
        properties.setProperty("key.deserializer", kafkaConfig.getKeyDeserializer());
        properties.setProperty("value.deserializer", kafkaConfig.getValueDeserializer());
    }

    @Async
    public void receiveTopicMessage(String methodName, String className, String topicName) throws KafkaException {
        if (StringUtils.isEmpty(methodName)) {
            throw new KafkaException(KafkaErrorEnumConstant.RECEIVE_MESSAGE_METHOD_EMPTY);
        }
        if (StringUtils.isEmpty(className)) {
            throw new KafkaException(KafkaErrorEnumConstant.RECEIVE_MESSAGE_CLASS_EMPTY);
        }
        if (StringUtils.isEmpty(topicName)) {
            throw new KafkaException(KafkaErrorEnumConstant.RECEIVE_MESSAGE_TOPIC_EMPTY);
        }
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(topicName), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                // 提交偏移量
                consumer.commitAsync();
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                // 获取该分区下已消费的偏移量
                long commitOffset = -1;
                for (TopicPartition topicPartition : partitions) {
                    commitOffset = consumer.committed(topicPartition).offset();
                    consumer.seek(topicPartition, commitOffset + 1);
                }
            }
        });

        while (true) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(100);
            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                logger.info("KafkaConsumeModule::receiveTopicMessage method = {}, clazz = {}, key = {}, " +
                                "offset = {}, message = {}", methodName, className, consumerRecord.key(),
                        consumerRecord.offset(), consumerRecord.value());
                try {
                    // 反射调用相关处理结果处理方法
                    // todo 这里一个理想的方式是添加一个实现类进行处理，每个具体处理的业务只需要自我实现方法就ok
                    StringBuilder messageBuild = new StringBuilder(consumerRecord.value());
                    Class clazz = Class.forName(className);
                    Method method = clazz.getMethod(methodName,StringBuilder.class);
                    method.invoke(clazz.newInstance(), messageBuild);
                } catch (Exception e) {
                    logger.error("KafkaConsumeModule::receiveTopicMessage 反射方法调用失败，methodName = {}, " +
                            "class = {},  exception = {}", methodName, className, e.getMessage());
                }
            }
        }
    }

}
