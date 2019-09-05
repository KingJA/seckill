package com.kingja.seckill.service;

import com.kingja.seckill.dao.MiaoshaUserDao;
import com.kingja.seckill.domain.MiaoshaUser;
import com.kingja.seckill.result.CodeMsg;
import com.kingja.seckill.vo.LoginVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public MiaoshaUser getById(int id) {
        return miaoshaUserDao.getById(id);
    }

    public CodeMsg login(LoginVo loginVo) {
        return null;
    }
}
