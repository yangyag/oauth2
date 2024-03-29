package com.yangyag.yangyagoauth.config;

import com.yangyag.yangyagoauth.auth.TokenAuthenticationFilter;
import com.yangyag.yangyagoauth.auth.TokenExceptionFilter;
import com.yangyag.yangyagoauth.auth.OAuth2SuccessHandler;
import com.yangyag.yangyagoauth.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final OAuthService oAuthService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() { // security를 적용하지 않을 리소스
        return web -> web.ignoring()
                // error endpoint를 열어줘야 함, favicon.ico 추가!
                .requestMatchers("/error", "/favicon.ico");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // rest api 설정
                .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화 -> cookie를 사용하지 않으면 꺼도 된다. (cookie를 사용할 경우 httpOnly(XSS 방어), sameSite(CSRF 방어)로 방어해야 한다.)
                .cors(AbstractHttpConfigurer::disable) // cors 비활성화 -> 프론트와 연결 시 따로 설정 필요
                .httpBasic(AbstractHttpConfigurer::disable) // 기본 인증 로그인 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // 기본 login form 비활성화
                .logout(AbstractHttpConfigurer::disable) // 기본 logout 비활성화
                .headers(header -> header
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                ) // X-Frame-Options 비활성화
                .sessionManagement(c ->
                        c.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용하지 않음

                // request 인증, 인가 설정
                .authorizeHttpRequests(request ->
                        request.requestMatchers(
                                PathRequest.toH2Console(),
                                new AntPathRequestMatcher("/"),
                                new AntPathRequestMatcher("/hello"),
                                new AntPathRequestMatcher("/success"),
                                new AntPathRequestMatcher("/publish"),
                                new AntPathRequestMatcher("/api.html")
                                ).permitAll()
                .anyRequest().authenticated()
                )

                // oauth2 설정
                .oauth2Login(
                        oauth -> // OAuth2 로그인 기능에 대한 여러 설정의 진입점
                        // OAuth2 공급자로부터 사용자 정보를 가져오고, 이 정보를 기반으로 애플리케이션 내의
                        // 사용자 인스턴스를 생성하거나 업데이트하는 역할
                        oauth.userInfoEndpoint(c -> c.userService(oAuthService))
                        // 로그인 성공 시 핸들러. 위 사용자 정보를 가지고 온 이후 실행됨.
                        // 위 두가지가 분리 된 이유는 단일책임원칙(SRP)에 의거함.
                        .successHandler(oAuth2SuccessHandler)

                 )// jwt 관련 설정
                .addFilterBefore(tokenAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new TokenExceptionFilter(), tokenAuthenticationFilter.getClass()); // 토큰 예외 핸들링;

        return http.build();
    }
}
