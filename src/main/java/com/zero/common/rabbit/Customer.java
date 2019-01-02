package com.zero.common.rabbit;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: Hogwarts
 * @Date: 2018/12/13
 */


//@Component
public class Customer {

    @RabbitListener(queues = "queue1")
    public void consumeMessage1(String message) {
        System.out.println("queue1接收消息：" + message);
    }

    @RabbitListener(queues = "queue2")
    public void consumeMessage2(String message) {
        System.out.println("queue2接收消息：" + message);
    }
}
