package com.kingja.seckill.rabbitmq;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:TODO
 * Create Time:2019/9/25 0025 上午 11:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Configuration
public class MQConfig {
    public static final String MIAOSHA_QUEUE = "miaosha.queue";
    public static final String QUEUE = "kQueue";
    public static final String TOPIC_QUEUE1 = "topic.Queue1";
    public static final String TOPIC_QUEUE2 = "topic.Queue2";
    public static final String HEADS_QUEUE = "head.Queue";
    public static final String TOPIC_EXCHANGE = "topicExchange";
    public static final String FANOUT_EXCHANGE = "fanoutExchange";
    public static final String HEADS_EXCHANGE = "headsExchange";

    /**
     * Direct 模式 直接发送
     */
    @Bean
    public Queue queue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public Queue miaoshaQueue() {
        return new Queue(MIAOSHA_QUEUE, true);
    }

    /**
     * Topic 模式 交换机 Exchange
     */
    @Bean
    public Queue topicQueue1() {
        return new Queue(TOPIC_QUEUE1, true);
    }

    /**
     * Topic 模式 交换机 可适配
     */
    @Bean
    public Queue topicQueue2() {
        return new Queue(TOPIC_QUEUE2, true);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding topicBinding1() {
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("topic.key1");
    }

    @Bean
    public Binding topicBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("topic.#");
    }

    /**
     * Fanout 模式 广播
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Binding fanoutBinding1() {
        return BindingBuilder.bind(topicQueue1()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(fanoutExchange());
    }

    /**
     * Heads 模式 广播
     */
    @Bean
    public HeadersExchange headsExchange() {
        return new HeadersExchange(HEADS_EXCHANGE);
    }

    @Bean
    public Queue headsQueue() {
        return new Queue(HEADS_QUEUE, true);
    }

    @Bean
    public Binding headsBinding() {
        Map<String, Object> map = new HashMap<>();
        map.put("heads1", "values1");
        map.put("heads2", "values2");
        return BindingBuilder.bind(headsQueue()).to(headsExchange()).whereAll(map).match();
    }
}
