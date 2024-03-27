package com.yangyag.yangyagoauth.service.impl;

import com.yangyag.yangyagoauth.model.Token;
import com.yangyag.yangyagoauth.repository.TokenRepository;
import com.yangyag.yangyagoauth.service.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    @Transactional
    public void saveOrUpdate(String userName, String refreshToken, String accessToken) {
        Token token = tokenRepository.findById(userName)
                .map(existingToken -> {
                    existingToken.setRefreshToken(refreshToken);
                    existingToken.setAccessToken(accessToken);
                    return existingToken;
                })
                .orElse(new Token(userName, refreshToken, accessToken));
        tokenRepository.save(token);
    }

    @Override
    public Token findByAccessTokenOrThrow(String accessToken) {
        return tokenRepository.findAll().stream()
                .filter(token -> accessToken.equals(token.getAccessToken()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("유효하지 않은 액세스 토큰입니다."));
    }

    @Override
    @Transactional
    public void updateToken(String newAccessToken, Token token) {
        token.setAccessToken(newAccessToken);
        tokenRepository.save(token);
    }
}
