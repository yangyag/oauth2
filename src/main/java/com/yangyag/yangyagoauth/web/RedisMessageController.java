package com.yangyag.yangyagoauth.web;

import com.yangyag.yangyagoauth.service.MessagePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RedisMessageController {
    private final MessagePublisher redisMessagePublisher;

    @PostMapping("/publish")
    public String publicMessage(@RequestParam String topic, @RequestParam String message) {
        redisMessagePublisher.publish(topic, message);

        return "Message published";
    }
}
