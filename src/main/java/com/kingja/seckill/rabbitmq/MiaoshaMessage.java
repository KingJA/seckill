package com.kingja.seckill.rabbitmq;

import com.kingja.seckill.domain.MiaoshaUser;

/**
 * Description:TODO
 * Create Time:2019/9/26 0026 下午 3:06
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class MiaoshaMessage {
    private MiaoshaUser user;
    private long goodsId;

    public MiaoshaUser getUser() {
        return user;
    }

    public void setUser(MiaoshaUser user) {
        this.user = user;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
