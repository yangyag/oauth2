package com.yangyag.yangyagoauth.service;

public interface MessagePublisher {
    void sendMessage(String topic, String message);
}
