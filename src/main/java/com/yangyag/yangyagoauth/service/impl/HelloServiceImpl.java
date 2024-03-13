package com.yangyag.yangyagoauth.service.impl;

import com.yangyag.yangyagoauth.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("Hello");
    }
}
