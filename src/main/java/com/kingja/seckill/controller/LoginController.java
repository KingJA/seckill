package com.kingja.seckill.controller;

import com.kingja.seckill.redis.RedisService;
import com.kingja.seckill.result.Result;
import com.kingja.seckill.service.MiaoshaUserService;
import com.kingja.seckill.service.UserService;
import com.kingja.seckill.vo.LoginVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description:TODO
 * Create Time:2019/9/3 0003 下午 3:12
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    MiaoshaUserService userService;
    @Autowired
    RedisService redisService;
    public static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/to_login")
    public String login() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(LoginVo loginVo) {
        logger.info(loginVo.toString());
        userService.login(loginVo);
        return Result.success(true);
    }
}
