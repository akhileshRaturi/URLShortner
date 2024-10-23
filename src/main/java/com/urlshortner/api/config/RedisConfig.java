package com.urlshortner.api.config;

import com.urlshortner.api.entity.Urls;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory){
            RedisTemplate<String, String> conn = new RedisTemplate<>();
            conn.setConnectionFactory(redisConnectionFactory);
            conn.setKeySerializer(new StringRedisSerializer());
            conn.setValueSerializer(new GenericJackson2JsonRedisSerializer());
            return  conn;
    }
}
