package com.yangyag.yangyagoauth.service.impl;

import com.yangyag.yangyagoauth.service.MessagePublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisMessagePublisher implements MessagePublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisMessagePublisher(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void publish(String topic, String message) {
        redisTemplate.convertAndSend(topic, message);
    }
}
