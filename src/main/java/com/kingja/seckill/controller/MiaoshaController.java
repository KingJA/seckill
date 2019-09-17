package com.kingja.seckill.controller;

import com.kingja.seckill.domain.MiaoshaOrder;
import com.kingja.seckill.domain.MiaoshaUser;
import com.kingja.seckill.domain.OrderInfo;
import com.kingja.seckill.redis.RedisService;
import com.kingja.seckill.result.CodeMsg;
import com.kingja.seckill.result.Result;
import com.kingja.seckill.service.GoodsService;
import com.kingja.seckill.service.MiaoshaService;
import com.kingja.seckill.service.MiaoshaUserService;
import com.kingja.seckill.service.OrderService;
import com.kingja.seckill.vo.GoodsVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Description:TODO
 * Create Time:2019/9/3 0003 下午 3:12
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {
    @Autowired
    MiaoshaUserService userService;
    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;
    @Autowired
    MiaoshaService miaoshaService;
    public static Logger logger = LoggerFactory.getLogger(MiaoshaController.class);

    @RequestMapping(value = "/do_miaosha",method = RequestMethod.POST)
    public Result<OrderInfo> miaosha(Model model, MiaoshaUser user, @RequestParam("goodsId") long goodsId) {
        logger.info("do_miaosha:");
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //判断库存
        GoodsVo goods = goodsService.getGoodsByGoodsId(goodsId);
        Integer stockCount = goods.getStockCount();
        if (stockCount <= 0) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //是否已经秒杀过了
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (miaoshaOrder != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
        // 减库存，下订单，写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
        return Result.success(orderInfo);
    }
}
