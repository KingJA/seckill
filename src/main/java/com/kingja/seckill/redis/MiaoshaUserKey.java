package com.kingja.seckill.redis;

/**
 * Description:TODO
 * Create Time:2019/9/4 0004 下午 5:21
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class MiaoshaUserKey extends BasePrefix {
    public static final int COOKIE_EXPIRE_SECONDS = 60 * 60 * 24 * 7;//7天
    public static final String COOKIE_NAME_TOKEN = "token";

    public static MiaoshaUserKey token = new MiaoshaUserKey("token");

    public MiaoshaUserKey(String prefix) {
        super(COOKIE_EXPIRE_SECONDS, prefix);
    }
}
