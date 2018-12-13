package com.zero.common.base.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: Hogwarts
 * @Date: 2018/12/13
 */

//@Configuration
public class RabbitConfig {

    //声明队列
    @Bean
    public Queue queue1() {
        return new Queue("queue1", true); // true表示持久化该队列
    }

    @Bean
    public Queue queue2() {
        return new Queue("queue2");
    }

    //交换机
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    //绑定
    @Bean
    public Binding binding1() {
        //通过key绑定
        return BindingBuilder.bind(queue1()).to(topicExchange()).with("key1");
    }


    @Bean
    public Binding binding2() {
        return BindingBuilder.bind(queue2()).to(topicExchange()).with("key2");
    }
}
