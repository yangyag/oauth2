package com.yangyag.yangyagoauth.web;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @GetMapping("/success")
    public String success(@RequestParam("accessToken") String accessToken) {
        return accessToken; // 쿼리 파라미터로 전달된 accessToken 반환
    }
}
