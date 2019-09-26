package com.kingja.seckill.rabbitmq;

import com.kingja.seckill.redis.RedisService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
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

    public void sendTopic(Object message) {
        String msg = RedisService.beanToString(message);
        logger.info("send topic Message：" + msg);
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key1", msg + 1);
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key2", msg + 2);
    }

    public void sendFanout(Object message) {
        String msg = RedisService.beanToString(message);
        logger.info("send fanout Message：" + msg);
        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE, "", msg);
    }

    public void sendHeads(Object message) {
        String msg = RedisService.beanToString(message);
        logger.info("send heads Message：" + msg);
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("heads1", "values1");
        messageProperties.setHeader("heads2", "values2");
        Message obj = new Message(msg.getBytes(), messageProperties);
        amqpTemplate.convertAndSend(MQConfig.HEADS_EXCHANGE, "", obj);
    }

    public void sendMiaoshaMessage(MiaoshaMessage miaoshaMessage) {
        String msg = RedisService.beanToString(miaoshaMessage);
        logger.info("send miaosha message：" + msg);
        amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE, msg);
    }
}
