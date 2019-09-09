package com.kingja.seckill.dao;

import com.kingja.seckill.domain.MiaoshaGoods;
import com.kingja.seckill.domain.MiaoshaOrder;
import com.kingja.seckill.vo.GoodsVo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2019/9/3 0003 下午 3:44
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Mapper
public interface GoodsDao {
    @Select("select g.*,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date from miaosha_goods mg left join " +
            "goods g on mg .goods_id=g.id")
    public List<GoodsVo> listGoodsVo();

    @Select("select g.*,mg.miaosha_price,mg.stock_count,mg.start_date,mg.end_date from miaosha_goods mg left join " +
            "goods g on mg.goods_id=g.id where g.id=#{goodsId}")
    public GoodsVo getGoodsByGoodsId(@Param("goodsId") long goodsId);

    @Update("update miaosha_goods set stock_count=stock_count -1 where goods_id=#{goodsId}")
    int reduceStock(MiaoshaGoods goods);
}
