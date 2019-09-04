package com.kingja.seckill.redis;

/**
 * Description:TODO
 * Create Time:2019/9/4 0004 下午 5:15
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class BasePrefix implements KeyPrefix{
    private int expireSeconds;
    private String prefix;

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {
        //默认0位永不过期
        return 0;
    }

    @Override
    public String getPrefix() {
        return null;
    }
}
