package com.yangyag.yangyagoauth.repository;

import com.yangyag.yangyagoauth.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, String> {
}
