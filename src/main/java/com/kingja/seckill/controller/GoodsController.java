package com.kingja.seckill.controller;

import com.kingja.seckill.domain.MiaoshaUser;
import com.kingja.seckill.redis.GoodsKey;
import com.kingja.seckill.redis.RedisService;
import com.kingja.seckill.result.Result;
import com.kingja.seckill.service.GoodsService;
import com.kingja.seckill.service.MiaoshaUserService;
import com.kingja.seckill.vo.GoodsDetailVo;
import com.kingja.seckill.vo.GoodsVo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;
    public static Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @RequestMapping(value = "/to_static_list",produces = "text/html")
    @ResponseBody
    public String staticlist(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user) {
        logger.info("to_static_list:");

        //有缓存则取缓存
        String html = redisService.get(GoodsKey.goodsList, "", String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        //没缓存则访问数据库
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(),
                model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_static_list", ctx);
        //存入缓存
        redisService.set(GoodsKey.goodsList, "", html);
        return html;
    }
    @RequestMapping(value = "/to_list",produces = "text/html")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user) {
        logger.info("to_list:");

        //有缓存则取缓存
        String html = redisService.get(GoodsKey.goodsList, "", String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        //没缓存则访问数据库
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(),
                model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        //存入缓存
        redisService.set(GoodsKey.goodsList, "", html);
        return html;
    }
    @RequestMapping(value = "/to_detail/{goodsId}",produces = "text/html")
    @ResponseBody
    public String detail(HttpServletRequest request, HttpServletResponse response,Model model, MiaoshaUser user, @PathVariable("goodsId") long goodsId) {
        logger.info("goodsId:" + goodsId);

        //有缓存则取缓存
        String html = redisService.get(GoodsKey.goodsDetail, ""+goodsId, String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        //没缓存则访问数据库
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
        model.addAttribute("goods", goods);
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);

        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(),
                model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
        //存入缓存
        redisService.set(GoodsKey.goodsDetail, ""+goodsId, html);
        return html;
    }

    @RequestMapping(value="/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> staticdetail(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user,
                                        @PathVariable("goodsId")long goodsId) {
        GoodsVo goods = goodsService.getGoodsByGoodsId(goodsId);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if(now < startAt ) {//秒杀还没开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int)((startAt - now )/1000);
        }else  if(now > endAt){//秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else {//秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoods(goods);
        vo.setUser(user);
        vo.setRemainSeconds(remainSeconds);
        vo.setMiaoshaStatus(miaoshaStatus);
        return Result.success(vo);
    }
}
