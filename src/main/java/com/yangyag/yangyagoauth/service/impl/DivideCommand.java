package com.yangyag.yangyagoauth.service.impl;

import com.yangyag.yangyagoauth.service.CalculatorCommand;
import org.springframework.stereotype.Service;

@Service("div")
public class DivideCommand implements CalculatorCommand {
    @Override
    public int execute(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return a / b;
    }
}
