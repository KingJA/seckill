package com.kingja.seckill.redis;

/**
 * Description:TODO
 * Create Time:2019/9/4 0004 下午 5:14
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public interface KeyPrefix {
    public int expireSeconds();

    public String getPrefix();
}
