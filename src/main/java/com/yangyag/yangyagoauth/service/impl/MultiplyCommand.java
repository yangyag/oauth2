package com.yangyag.yangyagoauth.service.impl;

import com.yangyag.yangyagoauth.service.CalculatorCommand;
import org.springframework.stereotype.Service;

@Service("mul")
public class MultiplyCommand implements CalculatorCommand {
    @Override
    public int execute(int a, int b) {
        return a * b;
    }
}