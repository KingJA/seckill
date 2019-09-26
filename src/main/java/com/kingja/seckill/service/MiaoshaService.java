package com.kingja.seckill.service;

import com.kingja.seckill.domain.MiaoshaOrder;
import com.kingja.seckill.domain.MiaoshaUser;
import com.kingja.seckill.domain.OrderInfo;
import com.kingja.seckill.redis.MiaoshaKey;
import com.kingja.seckill.redis.RedisService;
import com.kingja.seckill.vo.GoodsVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description:TODO
 * Create Time:2019/9/3 0003 下午 3:48
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Service
public class MiaoshaService {
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        //减库存 下订单 写入秒杀订单
        boolean success = goodsService.reduceStock(goods);
        if (success) {
            //order_info maiosha_order
            return orderService.createOrder(user, goods);
        } else {
            setGoodsOver(goods.getId());
            return null;
        }
    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKey.isGoodsOver, "" + goodsId, true);
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.get(MiaoshaKey.isGoodsOver, "" + goodsId, Boolean.class);
    }

    public long getMiaoshaResult(Long userId, long goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
        if (order != null) {
            return order.getOrderId();
        } else {
            boolean isOver = getGoodsOver(goodsId);
            if (isOver) {
                return -1;
            } else {
                return 0;
            }
        }
    }


}
