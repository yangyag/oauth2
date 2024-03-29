package com.yangyag.yangyagoauth.web;

import com.yangyag.yangyagoauth.service.HelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
public class HelloController {

    private final HelloService helloService;

    @GetMapping("/hello")
    public ResponseEntity<Void> helloTest() {
        helloService.sayHello();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/bye")
    public ResponseEntity<?> bye() {
        // "message": "bye" 형태의 JSON 응답을 생성
        return ResponseEntity.ok(Collections.singletonMap("message", "bye"));
    }
}
