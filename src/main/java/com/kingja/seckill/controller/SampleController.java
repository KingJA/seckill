package com.kingja.seckill.controller;

import com.kingja.seckill.domain.User;
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

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "kingja");
        return "hello";
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> getUser() {
        User user = userService.getById(1);
        return Result.success(user);
    }
}
