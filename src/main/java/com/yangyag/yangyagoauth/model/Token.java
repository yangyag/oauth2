package com.yangyag.yangyagoauth.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자를 생성
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    private String userName;
    private String refreshToken;
    private String accessToken;

    // 생성자, 게터, 세터 생략

    public Token(String userName, String refreshToken, String accessToken) {
        this.userName = userName;
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }
}
