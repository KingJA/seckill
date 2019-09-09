package com.kingja.seckill.dao;

import com.kingja.seckill.domain.MiaoshaOrder;
import com.kingja.seckill.domain.OrderInfo;
import com.kingja.seckill.vo.GoodsVo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2019/9/3 0003 下午 3:44
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Mapper
public interface OrderDao {


    @Select("select from miaosha_order where user_id=#{userId} and goods_id=#{goodsId} ")
    MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);

    long insert(OrderInfo orderInfo);

    int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);
}
