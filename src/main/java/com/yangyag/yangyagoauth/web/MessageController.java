package com.yangyag.yangyagoauth.web;

import com.yangyag.yangyagoauth.service.impl.KafkaProducer;
import com.yangyag.yangyagoauth.service.MessagePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {

    @Qualifier("kafkaProducer")
    private final MessagePublisher kafkaProducer;

    @Qualifier("redisPublisher")
    private final MessagePublisher redisPublisher;

    @PostMapping("/kafka")
    public String sendMessage(@RequestParam String topic, @RequestBody String message) {
        kafkaProducer.publish(topic, message);

        return "Message produced";
    }

    @PostMapping("/redis")
    public String publicMessage(@RequestParam String topic, @RequestParam String message) {
        redisPublisher.publish(topic, message);

        return "Message published";
    }
}
