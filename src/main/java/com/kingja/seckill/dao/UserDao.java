package com.kingja.seckill.dao;

import com.kingja.seckill.domain.User;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Description:TODO
 * Create Time:2019/9/3 0003 下午 3:44
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Mapper
public interface UserDao {
    @Select("select * from user where id = #{id}")
    public User getById(@Param("id") int id);

    @Insert("insert into user(id,name)values(#{id},#{name})")
    int insert(User user);
}
