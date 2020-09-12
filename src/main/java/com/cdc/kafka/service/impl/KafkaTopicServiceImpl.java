package com.cdc.kafka.service.impl;

import com.cdc.kafka.constant.KafkaErrorEnumConstant;
import com.cdc.kafka.entity.TopicEntity;
import com.cdc.kafka.exception.KafkaException;
import com.cdc.kafka.service.KafkaTopicService;
import com.cdc.kafka.utils.ValidationUtil;
import kafka.utils.Json;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * @author cuidc
 */
@Service
public class KafkaTopicServiceImpl implements KafkaTopicService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaTopicServiceImpl.class);
    /**
     * 默认超时时间5s
     */
    private static final Integer DEFAULT_TIMEOUT = 5000;
    private static final String DEFAULT_BOOTSTRAP_SERVERS = "localhost:9092";

    private String bootstrapServers;
    private AdminClient adminClient;

    @Autowired
    private Environment environment;

    @PostConstruct
    void initMethod() {
        bootstrapServers = environment.getProperty("zookeeper.server.url", DEFAULT_BOOTSTRAP_SERVERS);
        Properties properties = new Properties();
        properties.setProperty(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        adminClient = KafkaAdminClient.create(properties);
    }

    @Override
    public void createTopic(TopicEntity topicEntity) throws KafkaException {
        if (topicEntity == null) {
            throw new KafkaException(KafkaErrorEnumConstant.TOPIC_ENTITY_NULL);
        }
        if (StringUtils.isEmpty(topicEntity.getTopicName())) {
            throw new KafkaException(KafkaErrorEnumConstant.TOPIC_NAME_EMPTY);
        }
        if (topicEntity.getPartition() == null || topicEntity.getPartition() < 1) {
            throw new KafkaException(KafkaErrorEnumConstant.TOPIC_PARTITION_INVALID);
        }
        if (topicEntity.getReplication() == null || topicEntity.getReplication() < 1) {
            throw new KafkaException(KafkaErrorEnumConstant.TOPIC_REPLICATION_INVALID);
        }
        logger.info("KafkaTopicServiceImpl::createTopic create kafka topic! topicEntity = {}",
                Json.encodeAsString(topicEntity));
        // 获取topic列表
        List<String> topicNameList = listTopic();
        if (topicNameList.contains(topicEntity.getTopicName())) {
            throw new KafkaException(KafkaErrorEnumConstant.TOPIC_NAME_EXIST);
        }

        // 创建topic
        NewTopic newTopic = new NewTopic(topicEntity.getTopicName(), topicEntity.getPartition(),
                topicEntity.getReplication());
        CreateTopicsResult topicsResult = adminClient.createTopics(Collections.singletonList(newTopic));
        try {
            topicsResult.all().get(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("KafkaTopicServiceImpl::createTopic create topic fail! bootStrapServers = {}, " +
                            "topicName = {}, partition = {}, replication = {}, errorMesg = {}", bootstrapServers,
                    topicEntity.getTopicName(), topicEntity.getPartition(), topicEntity.getReplication(), e.getMessage());
            throw new KafkaException(KafkaErrorEnumConstant.EXCEPTION_INTERRUPT);
        } catch (ExecutionException e) {
            logger.error("KafkaTopicServiceImpl::createTopic create topic fail! bootStrapServers = {}, " +
                            "topicName = {}, partition = {}, replication = {}, errorMesg = {}", bootstrapServers,
                    topicEntity.getTopicName(), topicEntity.getPartition(), topicEntity.getReplication(), e.getMessage());
            throw new KafkaException(KafkaErrorEnumConstant.EXCEPTION_EXECUTION);
        } catch (TimeoutException e) {
            logger.error("KafkaTopicServiceImpl::createTopic create topic fail! bootStrapServers = {}, " +
                            "topicName = {}, partition = {}, replication = {}, errorMesg = {}", bootstrapServers,
                    topicEntity.getTopicName(), topicEntity.getPartition(), topicEntity.getReplication(), e.getMessage());
            throw new KafkaException(KafkaErrorEnumConstant.EXCEPTION_TIMEOUT);
        }
    }

    @Override
    public List<String> listTopic() throws KafkaException {

        // 调kafka接口获取topic列表
        ListTopicsResult listTopicsResult = adminClient.listTopics();
        KafkaFuture<Set<String>> names = listTopicsResult.names();
        Set<String> topicNameSet;
        try {
            topicNameSet = names.get(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            logger.error("KafkaTopicServiceImpl::listTopic listing the topic name happens interrupted exception, " +
                    "bootstrap.servers = {} message = {}", bootstrapServers, e.getMessage());
            throw new KafkaException(KafkaErrorEnumConstant.EXCEPTION_INTERRUPT);
        } catch (ExecutionException e) {
            logger.error("KafkaTopicServiceImpl::listTopic listing the topic name happens execution exception, " +
                    "bootstrap.servers = {} message = {}", bootstrapServers, e.getMessage());
            throw new KafkaException(KafkaErrorEnumConstant.EXCEPTION_EXECUTION);
        } catch (TimeoutException e) {
            logger.error("KafkaTopicServiceImpl::listTopic listing the topic name happens timeout exception, " +
                    "bootstrap.servers = {} message = {}", bootstrapServers, e.getMessage());
            throw new KafkaException(KafkaErrorEnumConstant.EXCEPTION_TIMEOUT);
        }
        if (topicNameSet == null || topicNameSet.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(topicNameSet);
    }

    @Override
    public void deleteTopic(List<String> topicNames) throws KafkaException {
        if (ValidationUtil.emptyList(topicNames)) {
            throw new KafkaException(KafkaErrorEnumConstant.TOPIC_DELETE_TOPIC_EMPTY);
        }
        // 已存在的topic列表
        List<String> topicListExistInKafka = listTopic();
        if (ValidationUtil.emptyList(topicListExistInKafka)) {
            throw new KafkaException(KafkaErrorEnumConstant.TOPIC_EMPTY_EXIST);
        }
        // 获取kafka中存在且需要被删除的topic
        List<String> topicToBeDeleted = topicListExistInKafka.stream().filter(topicNames::contains)
                .collect(Collectors.toList());
        // kafka配置里要删除的topic不存在的列表
        List<String> topicNotExist = topicNames.stream().filter(topic -> !topicListExistInKafka.contains(topic))
                .collect(Collectors.toList());
        if (ValidationUtil.emptyList(topicToBeDeleted)) {
            throw new KafkaException(KafkaErrorEnumConstant.TOPIC_DELETE_TOPIC_EMPTY);
        }
        // 删除topic
        DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(topicToBeDeleted);
        Map<String, KafkaFuture<Void>> futuresMap = deleteTopicsResult.values();
        List<String> topicDeleteFailList = new ArrayList<>();
        List<String> topicDeleteSuccessList = new ArrayList<>();
        futuresMap.forEach((topicName, future) -> {
            try {
                future.get(20, TimeUnit.SECONDS);
                topicDeleteSuccessList.add(topicName);
            } catch (Exception e) {
                logger.error("KafkaTopicServiceImpl::deleteTopic delete topic fail! topicName = {}, errorMsg = {}",
                        topicName, e.getMessage());
                topicDeleteFailList.add(topicName);
            }
        });
        if (!ValidationUtil.emptyList(topicDeleteFailList)) {
            String errorMsg = "需要删除的topic列表：" + String.join(",", topicNames)
                    + "成功删除的topic列表：" + String.join(",", topicDeleteSuccessList)
                    + "不存在的topic列表：" + String.join(",", topicNotExist)
                    + "删除失败的topic列表：" + String.join(",", topicDeleteFailList);
            throw new KafkaException(KafkaErrorEnumConstant.EXCEPTION_FAIL.getErrorCode(), errorMsg);
        }
    }

}
