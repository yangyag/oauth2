package com.yangyag.yangyagoauth.service.impl;

import com.yangyag.yangyagoauth.service.MessagePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service("kafka")
@RequiredArgsConstructor
public class KafkaProducer implements MessagePublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}
