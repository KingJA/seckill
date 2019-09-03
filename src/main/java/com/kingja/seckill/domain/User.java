package com.kingja.seckill.domain;

/**
 * Description:TODO
 * Create Time:2019/9/3 0003 下午 3:43
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class User {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return null == name ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
