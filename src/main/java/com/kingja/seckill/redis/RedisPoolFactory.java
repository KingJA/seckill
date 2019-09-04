package com.kingja.seckill.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Description:TODO
 * Create Time:2018/8/6 20:09
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
@Service
public class RedisPoolFactory {

    @Autowired
    RedisConfig redisConfig;
    @Bean
    public JedisPool JedisPoolFactory() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);
        JedisPool jedisPool = new JedisPool(poolConfig, redisConfig.getHost(), redisConfig.getPort(), redisConfig
                .getTimeout() * 1000, redisConfig.getPassword(), 0);
        System.out.println("getPassword:"+redisConfig.getPassword());
        return jedisPool;
    }
}
