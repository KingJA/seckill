package com.kingja.seckill.service;

import com.kingja.seckill.dao.MiaoshaUserDao;
import com.kingja.seckill.domain.MiaoshaUser;
import com.kingja.seckill.exception.ResultException;
import com.kingja.seckill.redis.MiaoshaUserKey;
import com.kingja.seckill.redis.RedisService;
import com.kingja.seckill.result.CodeMsg;
import com.kingja.seckill.util.Md5Util;
import com.kingja.seckill.util.UUIDUtil;
import com.kingja.seckill.vo.LoginVo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Description:TODO
 * Create Time:2019/9/3 0003 下午 3:48
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Service
public class MiaoshaUserService {
    @Autowired
    MiaoshaUserDao miaoshaUserDao;

    @Autowired
    RedisService redisService;

    public MiaoshaUser getById(int id) {
        //1.有缓存则取缓存
        //2.没缓存取数据库
        //3.保存如缓存
        MiaoshaUser user = redisService.get(MiaoshaUserKey.getById, "" + id, MiaoshaUser.class);
        if (user != null) {
            return user;
        }
        user = miaoshaUserDao.getById(id);
        if (user != null) {
            redisService.set(MiaoshaUserKey.getById, "" + id, user);
        }
        return user;
    }

    public String login(HttpServletResponse httpServletResponse, LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        MiaoshaUser user = miaoshaUserDao.getById(Long.valueOf(mobile));
        if (user == null) {
            throw new ResultException(CodeMsg.MOBILE_ERROR);
        }
        String dbPassword = user.getPassword();
        String dbSalt = user.getSalt();

        if (!Md5Util.formPassToDBPass(password, dbSalt).equals(dbPassword)) {
            throw new ResultException(CodeMsg.PASSWORD_ERROR);
        }
        //生成cookie
        String token = UUIDUtil.uuid();
        addCookie(httpServletResponse, token, user);
        return token;
    }

    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        MiaoshaUser miaoshaUser = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        if (miaoshaUser != null) {
            addCookie(response, token, miaoshaUser);
        }
        return miaoshaUser;
    }

    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
        redisService.set(MiaoshaUserKey.token, token, user);
        Cookie cookie = new Cookie(MiaoshaUserKey.COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
