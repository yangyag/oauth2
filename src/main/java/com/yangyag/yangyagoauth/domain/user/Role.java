package com.yangyag.yangyagoauth.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;

    public static Optional<Role> findByTitleContains(String title) {
        return Arrays.stream(Role.values())
                .filter(role -> role.title.contains(title))
                .findFirst();
    }
}
