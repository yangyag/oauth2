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
    public WebSecurityCustomizer webSecurityCustomizer() { // 보안 설정에서 제외할 리소스 설정
        return web -> web.ignoring()
                .requestMatchers("/error", "/favicon.ico"); // /error, /favicon.ico 엔드포인트는 보안 설정에서 제외
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // REST API 설정
                .csrf(AbstractHttpConfigurer::disable) // CSRF 공격 방지 기능 비활성화 (stateless한 서비스에서는 필요 없음)
                .cors(AbstractHttpConfigurer::disable) // CORS 설정 비활성화 (프론트엔드와 연결할 때 별도로 설정 필요)
                .httpBasic(AbstractHttpConfigurer::disable) // 기본 인증 로그인 방식 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // 기본 폼 로그인 방식 비활성화
                .logout(AbstractHttpConfigurer::disable) // 기본 로그아웃 방식 비활성화
                .headers(header -> header
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                ) // X-Frame-Options 헤더 설정 (동일 출처에서만 프레임 렌더링 허용)
                .sessionManagement(c ->
                        c.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 안 함 (stateless)

                // 요청에 대한 인증 및 권한 설정
                .authorizeHttpRequests(request ->
                        request.requestMatchers(
                                        PathRequest.toH2Console(), // H2 콘솔 접근 허용
                                        new AntPathRequestMatcher("/"), // 루트 경로 접근 허용
                                        new AntPathRequestMatcher("/hello"), // /hello 엔드포인트 접근 허용
                                        new AntPathRequestMatcher("/success"), // /success 엔드포인트 접근 허용
                                        new AntPathRequestMatcher("/api.html"), // /api.html 엔드포인트 접근 허용
                                        new AntPathRequestMatcher("/api/messages/redis"), // /api/messages/redis 엔드포인트 접근 허용
                                        new AntPathRequestMatcher("/api/messages/kafka") // /api/messages/kafka 엔드포인트 접근 허용
                                ).permitAll()
                                .anyRequest().authenticated() // 그 외의 요청은 인증 필요
                )

                // OAuth2 인증 설정
                .oauth2Login(
                        oauth ->
                                oauth.userInfoEndpoint(c -> c.userService(oAuthService)) // OAuth2 공급자로부터 사용자 정보를 가져와 애플리케이션 내 사용자 생성/업데이트
                                        .successHandler(oAuth2SuccessHandler) // OAuth2 인증 성공 후 처리 핸들러 설정
                )

                // JWT 인증 필터 설정
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // JWT 인증 필터 추가
                .addFilterBefore(new TokenExceptionFilter(), tokenAuthenticationFilter.getClass()); // JWT 예외 처리 필터 추가

        return http.build();
    }
}