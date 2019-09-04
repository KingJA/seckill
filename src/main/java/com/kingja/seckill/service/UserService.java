package com.kingja.seckill.service;

import com.kingja.seckill.dao.UserDao;
import com.kingja.seckill.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public boolean tx() {
        User user2 = new User();
        user2.setId(2);
        user2.setName("222");
        userDao.insert(user2);

        User user1 = new User();
        user1.setId(1);
        user1.setName("111");
        userDao.insert(user1);

        return true;
    }
}
