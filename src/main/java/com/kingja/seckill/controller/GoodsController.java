package com.kingja.seckill.controller;

import com.kingja.seckill.domain.MiaoshaUser;
import com.kingja.seckill.redis.RedisService;
import com.kingja.seckill.service.GoodsService;
import com.kingja.seckill.service.MiaoshaUserService;
import com.kingja.seckill.vo.GoodsVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * Description:TODO
 * Create Time:2019/9/3 0003 下午 3:12
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    MiaoshaUserService userService;
    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;
    public static Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @RequestMapping("/to_list")
    public String list(Model model, MiaoshaUser user) {
        logger.info("to_list:");
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String detail(Model model, MiaoshaUser user, @PathVariable("goodsId") long goodsId) {
        logger.info("goodsId:"+goodsId);
        model.addAttribute("user", user);
        GoodsVo goods = goodsService.getGoodsByGoodsId(goodsId);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long nowTime = System.currentTimeMillis();
        int miaoshaStatus;
        int remainSeconds = 0;

        if (nowTime < startAt) {
            //还未开始
            miaoshaStatus = 0;
            remainSeconds = (int) ((startAt - nowTime) / 1000);
        } else if (nowTime > endAt) {
            //已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {
            //进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        logger.info("miaoshaStatus:"+miaoshaStatus);
        model.addAttribute("goods", goods);
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }
}
