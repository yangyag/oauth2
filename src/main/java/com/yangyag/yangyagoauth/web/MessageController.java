package com.yangyag.yangyagoauth.web;

import com.yangyag.yangyagoauth.service.MessagePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {

    private final Map<String, MessagePublisher> messagePublisher;

    @PostMapping("/{messagingSystem}")
    public String sendMessage(@PathVariable String messagingSystem,
                              @RequestParam String topic,
                              @RequestParam String message) {

        messagePublisher.get(messagingSystem).sendMessage(topic, message);

        return "Message has been sent successful";
    }
}
