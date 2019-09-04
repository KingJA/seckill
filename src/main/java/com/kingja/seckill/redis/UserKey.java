package com.kingja.seckill.redis;

/**
 * Description:TODO
 * Create Time:2019/9/4 0004 下午 5:21
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class UserKey extends BasePrefix {

    public UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
