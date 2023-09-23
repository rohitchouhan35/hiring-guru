package com.rohitchouhan35.hiringmadeeasy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {
    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(20); // Adjust the pool size as needed
        poolConfig.setMinIdle(5);

        return new JedisPool(poolConfig, "localhost", 3513);
    }
}

