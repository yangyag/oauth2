package com.yangyag.yangyagoauth.service.impl;

import com.yangyag.yangyagoauth.service.MessagePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service("redisPublisher")
@RequiredArgsConstructor
public class RedisPublisher implements MessagePublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void publish(String topic, String message) {
        redisTemplate.convertAndSend(topic, message);
    }
}
