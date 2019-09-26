package com.kingja.seckill.controller;

import com.kingja.seckill.domain.MiaoshaOrder;
import com.kingja.seckill.domain.MiaoshaUser;
import com.kingja.seckill.rabbitmq.MQSender;
import com.kingja.seckill.rabbitmq.MiaoshaMessage;
import com.kingja.seckill.redis.GoodsKey;
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
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

/**
 * Description:TODO
 * Create Time:2019/9/3 0003 下午 3:12
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {
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

    @Autowired
    MQSender sender;
    private HashMap<Long, Boolean> localOverMap =  new HashMap<Long, Boolean>();
    public static Logger logger = LoggerFactory.getLogger(MiaoshaController.class);

    @RequestMapping(value = "/do_miaosha", method = RequestMethod.POST)
    public Result<Integer> miaosha(Model model, MiaoshaUser user, @RequestParam("goodsId") long goodsId) {
        logger.info("do_miaosha:");
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        //内存标记，减少redis访问
        boolean over = localOverMap.get(goodsId);
        if(over) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        /*预减库存*/
        long stock = redisService.decr(GoodsKey.goodsStock, "" + goodsId);
        if (stock < 0) {
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //是否已经秒杀过了
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (miaoshaOrder != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }

        MiaoshaMessage miaoshaMessage = new MiaoshaMessage();
        miaoshaMessage.setUser(user);
        miaoshaMessage.setGoodsId(goodsId);
        sender.sendMiaoshaMessage(miaoshaMessage);
        return Result.success(0);
    }

    /**
     * 系统初始化
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("afterPropertiesSet:");
        //1.系统初始化，将商品库存加载到redis
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        if (goodsList == null) {
            return;
        }
        for (GoodsVo goods : goodsList) {
            redisService.set(GoodsKey.goodsStock, "" + goods.getId(), goods.getStockCount());
        }
    }

    /**
     * orderId：成功
     * -1：秒杀失败
     * 0：排队中
     */
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public Result<Long> miaoshaResult(Model model, MiaoshaUser user, @RequestParam("goodsId") long goodsId) {
        logger.info("miaoshaResult:");
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long result = miaoshaService.getMiaoshaResult(user.getId(), goodsId);
        return Result.success(result);
    }
}
