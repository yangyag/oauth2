package com.yangyag.yangyagoauth.service.impl;


import com.yangyag.yangyagoauth.service.CalculatorCommand;
import org.springframework.stereotype.Service;

@Service("add")
public class AddCommand implements CalculatorCommand {
    @Override
    public int execute(int a, int b) {
        return a + b;
    }
}