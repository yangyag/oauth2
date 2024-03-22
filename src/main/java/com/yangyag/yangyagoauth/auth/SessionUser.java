package com.yangyag.yangyagoauth.auth;

import com.yangyag.yangyagoauth.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
// User 엔티티를 직접 직렬화하지 않고 추가 dto를 생성하여 직렬화 해주었다.
public class SessionUser implements Serializable {

    // 인증된 사용자 정보만 저장
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
