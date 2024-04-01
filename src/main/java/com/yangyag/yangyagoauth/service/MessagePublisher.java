package com.yangyag.yangyagoauth.service;

public interface MessagePublisher {
    void publish(String topic, String message);
}
