package com.zero.common.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: Hogwarts
 * @Date: 2018/12/13
 */


//@Component
public class Product {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send1(String message) {
        System.out.println("queue1发送消息：" + message);
        rabbitTemplate.convertAndSend("topicExchange", "key1", message);
    }

    public void send2(String message) {
        System.out.println("queue2发送消息：" + message);
        rabbitTemplate.convertAndSend("topicExchange", "key2", message);
    }
}
