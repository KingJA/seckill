package com.kingja.seckill.controller;

import com.kingja.seckill.domain.User;
import com.kingja.seckill.rabbitmq.MQSender;
import com.kingja.seckill.redis.RedisService;
import com.kingja.seckill.redis.UserKey;
import com.kingja.seckill.result.Result;
import com.kingja.seckill.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description:TODO
 * Create Time:2019/9/3 0003 下午 3:12
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Controller
@RequestMapping("/demo")
public class SampleController {
    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;

    @Autowired
    MQSender mqSender;
    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "kingja");
        return "hello";
    }

    @RequestMapping("/mq")
    @ResponseBody
    public Result<String> mq() {
        mqSender.send("hello，KingJA");
        return Result.success("发送队列");
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> getUser() {
        User user = userService.getById(1);
        return Result.success(user);
    }
    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisget() {
        User user = redisService.get(UserKey.getById,"1",User.class);
        return Result.success(user);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisset() {
        User user = new User();
        user.setId(888);
        user.setName("Jordan");
        Boolean result = redisService.set(UserKey.getById,"1",user);
        return Result.success(result);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> tx() {
        userService.tx();
        return Result.success(true);
    }
}
