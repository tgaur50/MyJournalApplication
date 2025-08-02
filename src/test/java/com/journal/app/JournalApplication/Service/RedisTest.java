package com.journal.app.JournalApplication.Service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Disabled
    @Test
    public void testRedis(){
        redisTemplate.opsForValue().set("email","tushargaur9913103636@gmail.com");
        Object email = redisTemplate.opsForValue().get("email");
        Object sal = redisTemplate.opsForValue().get("salary");
        int a = 1;
    }
}
