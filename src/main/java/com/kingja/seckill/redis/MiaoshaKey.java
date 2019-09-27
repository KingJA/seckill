package com.kingja.seckill.redis;

/**
 * Description:TODO
 * Create Time:2019/9/4 0004 下午 5:21
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class MiaoshaKey extends BasePrefix {


    public MiaoshaKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static MiaoshaKey isGoodsOver = new MiaoshaKey(0, "isGoodsOver");
    public static MiaoshaKey miaoshaPath = new MiaoshaKey(60, "miaoshaPath");
    public static MiaoshaKey getMiaoshaVerifyCode = new MiaoshaKey(300, "iaoshaVerifyCode");
}
