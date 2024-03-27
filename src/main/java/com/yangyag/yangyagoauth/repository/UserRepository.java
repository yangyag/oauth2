package com.yangyag.yangyagoauth.repository;

import com.yangyag.yangyagoauth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 소셜 로그인으로 반환되는 값 중 email을 통해 사용자의 가입 여부를 판단하기 위한 메소드
    Optional<User> findByEmail(String email);
}
