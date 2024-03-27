package com.yangyag.yangyagoauth.service;

import com.yangyag.yangyagoauth.model.Token;

public interface TokenService {
    void saveOrUpdate(String userName, String refreshToken, String accessToken);
    Token findByAccessTokenOrThrow(String accessToken);
    void updateToken(String newAccessToken, Token token);
}
