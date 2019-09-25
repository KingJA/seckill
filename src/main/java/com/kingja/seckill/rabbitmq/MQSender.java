package com.kingja.seckill.rabbitmq;

import com.kingja.seckill.redis.RedisService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:TODO
 * Create Time:2019/9/25 0025 上午 11:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Service
public class MQSender {
    private static Logger logger = LoggerFactory.getLogger(MQSender.class);
    @Autowired
    AmqpTemplate amqpTemplate;

    public void send(Object message) {
        String msg = RedisService.beanToString(message);
        logger.info("send message：" + msg);
        amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);
    }
}
