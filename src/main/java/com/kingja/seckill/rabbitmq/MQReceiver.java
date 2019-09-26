package com.kingja.seckill.rabbitmq;

import com.kingja.seckill.domain.MiaoshaOrder;
import com.kingja.seckill.domain.MiaoshaUser;
import com.kingja.seckill.domain.OrderInfo;
import com.kingja.seckill.redis.RedisService;
import com.kingja.seckill.result.CodeMsg;
import com.kingja.seckill.result.Result;
import com.kingja.seckill.service.GoodsService;
import com.kingja.seckill.service.MiaoshaService;
import com.kingja.seckill.service.OrderService;
import com.kingja.seckill.vo.GoodsVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:TODO
 * Create Time:2019/9/25 0025 上午 11:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Service
public class MQReceiver {
    private static Logger logger = LoggerFactory.getLogger(MQReceiver.class);
    @Autowired
    OrderService orderService;
    @Autowired
    MiaoshaService miaoshaService;
    @Autowired
    GoodsService goodsService;

    /**
     * Direct 模式 交换机 Exchange
     *
     * @param message
     */
    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String message) {
        logger.info("receive QUEUE message：" + message);
    }

    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void receiveMiaosha(String message) {
        logger.info("receive MiaoshaQUEUE message：" + message);
        MiaoshaMessage miaoshaMessage = RedisService.stringToBean(message, MiaoshaMessage.class);
        MiaoshaUser user = miaoshaMessage.getUser();
        long goodsId = miaoshaMessage.getGoodsId();
        GoodsVo goods = goodsService.getGoodsByGoodsId(goodsId);
        Integer stockCount = goods.getStockCount();
        if (stockCount <= 0) {
            return;
        }
        //是否已经秒杀过了
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (miaoshaOrder != null) {
            return;
        }
        // 减库存，下订单，写入秒杀订单
        miaoshaService.miaosha(user, goods);
    }


    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        logger.info("receive TOPIC_QUEUE1 message：" + message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        logger.info("receive TOPIC_QUEUE2 message：" + message);
    }


    @RabbitListener(queues = MQConfig.HEADS_QUEUE)
    public void receiveHeads(byte[] message) {
        logger.info("receive HEADS_QUEUE heads message：" + new String(message));
    }
}
