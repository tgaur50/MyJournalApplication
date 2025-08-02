package com.journal.app.JournalApplication.Config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;

import java.util.Arrays;

@Configuration
public class RedisConfig {

    @Autowired
    private Environment environment;

    @Value("${spring.redis.host}")
    private String redisHost;

    @PostConstruct
    public void logRedisHost() {
        System.out.println("✅ Connected Redis Host: " + redisHost);
    }

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostConstruct
    public void testRedisConnection() {
        try {
            redisTemplate.opsForValue().set("ping", "pong");
            String result = redisTemplate.opsForValue().get("ping");
            System.out.println("✅ Redis connected. Value: " + result);
        } catch (Exception e) {
            System.err.println("❌ Redis connection failed:");
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void logActiveProfiles() {
        System.out.println("🔧 Active Spring Profile(s): " + Arrays.toString(environment.getActiveProfiles()));
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(
            @Value("${spring.redis.host}") String host,
            @Value("${spring.redis.port}") int port,
            @Value("${spring.redis.password:}") String password) {

        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(host);
        config.setPort(port);
        if (!password.isBlank()) {
            config.setPassword(RedisPassword.of(password));
        }

        return new LettuceConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }
}
