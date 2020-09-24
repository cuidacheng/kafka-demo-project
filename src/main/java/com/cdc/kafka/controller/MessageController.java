package com.cdc.kafka.controller;

import com.cdc.kafka.service.KafkaConsumerService;
import com.cdc.kafka.service.KafkaProducerService;
import com.cdc.kafka.vo.ReceiveMessageRequestVO;
import com.cdc.kafka.vo.SendMessageRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息生产与消费控制器
 *
 * @author: cuidc
 * @date: 2020-09-23 23:31
 */

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private KafkaProducerService producerService;
    @Autowired
    private KafkaConsumerService consumerService;

    @RequestMapping("/sendMessage")
    public void sendMessage(@RequestBody SendMessageRequestVO requestVO) {
        producerService.sendTopicMessage(requestVO.getTopicName(), requestVO.getMessage());
    }

    @RequestMapping("/receiveMessage")
    public void receiveMessage(@RequestBody ReceiveMessageRequestVO requestVO) {
        consumerService.receiveTopicMessage(requestVO.getTopicName());
    }
}
