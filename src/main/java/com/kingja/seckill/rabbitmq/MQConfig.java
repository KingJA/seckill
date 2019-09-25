package com.kingja.seckill.rabbitmq;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:TODO
 * Create Time:2019/9/25 0025 上午 11:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Configuration
public class MQConfig {
    public static final String QUEUE = "kQueue";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE, true);
    }
}
