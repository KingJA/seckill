package com.kingja.seckill.redis;

/**
 * Description:TODO
 * Create Time:2019/9/4 0004 下午 5:21
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class GoodsCacheKey extends BasePrefix {

    public static GoodsCacheKey goodsList = new GoodsCacheKey("goodsList");
    public static GoodsCacheKey goodsDetail = new GoodsCacheKey("goodsDetail");

    public GoodsCacheKey(String prefix) {
        super(180, prefix);
    }
}
