package com.kingja.seckill.service;

import com.kingja.seckill.dao.UserDao;
import com.kingja.seckill.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:TODO
 * Create Time:2019/9/3 0003 下午 3:48
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public User getById(int id) {
        return userDao.getById(id);
    }
}
