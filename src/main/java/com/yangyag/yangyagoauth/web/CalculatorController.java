package com.yangyag.yangyagoauth.web;

import com.yangyag.yangyagoauth.service.CalculatorCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CalculatorController {

    private final Map<String, CalculatorCommand> commands;

    @PostMapping("/calculate")
    public ResponseEntity<Integer> calculate(@RequestParam("command") String command,
                                             @RequestParam("a") int a,
                                             @RequestParam("b") int b) {

        var result = Optional.ofNullable(commands.get(command))
                .orElseThrow(() -> new IllegalArgumentException("Invalid command: " + command))
                .execute(a, b);

        return ResponseEntity.ok(result);
    }
}
