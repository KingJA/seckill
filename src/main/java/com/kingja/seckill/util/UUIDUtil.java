package com.kingja.seckill.util;

import java.util.UUID;

/**
 * Description:TODO
 * Create Time:2018/8/12 16:09
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-","");
    }
}
