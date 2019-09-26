package com.kingja.seckill.redis;

/**
 * Description:TODO
 * Create Time:2019/9/4 0004 下午 5:21
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class GoodsKey extends BasePrefix {

    public static GoodsKey goodsList = new GoodsKey("goodsList");
    public static GoodsKey goodsDetail = new GoodsKey("goodsDetail");
    public static GoodsKey goodsStock = new GoodsKey("goodsStock");

    public GoodsKey(String prefix) {
        super( prefix);
    }
}
