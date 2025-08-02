package com.journal.app.JournalApplication.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {
    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key, Class<T> entityClass)  {
        try {
            Object o = redisTemplate.opsForValue().get(key);
            ObjectMapper om = new ObjectMapper();
            return om.readValue(o.toString(), entityClass);
        }catch (Exception e){
            e.printStackTrace();
            log.error("Exception: " + e);
            return null;
        }
    }

    public void set(String key, Object o, Long ttl) {
        try {
            ObjectMapper objectMap = new ObjectMapper();
            String jsonResponse = objectMap.writeValueAsString(o);
            redisTemplate.opsForValue().set(key, jsonResponse, ttl, TimeUnit.SECONDS);
        }catch (Exception e){
            e.printStackTrace();
            log.error("Exception: " + e);
        }
    }
}
