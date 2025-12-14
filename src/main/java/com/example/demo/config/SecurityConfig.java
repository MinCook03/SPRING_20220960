package com.example.demo.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer; // [필수]
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // [핵심 해결책] 
    // index.html에서 사용하는 폴더명(img, lib)을 여기에 반드시 추가해야 합니다.
    // ignoring()을 사용하면 스프링 시큐리티가 이 경로들은 아예 검사하지 않습니다.
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                // 1. 스프링 부트 기본 정적 리소스 경로 무시 (css, js, images, webjars, favicon)
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                
                // 2. [중요] 사용자가 추가한 실제 폴더명 명시적 허용
                // index.html을 보면 "img" 폴더와 "lib" 폴더를 쓰고 있습니다.
                .requestMatchers(
                        "/css/**", 
                        "/js/**", 
                        "/img/**",    // [필수] images가 아니라 img 폴더를 허용
                        "/lib/**",    // [필수] 부트스트랩/JS 라이브러리 폴더 허용
                        "/favicon.ico", 
                        "/error"
                );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. 보안 헤더 및 CSRF 설정
            .headers(headers -> headers
                .addHeaderWriter((request, response) -> {
                    response.setHeader("X-XSS-Protection", "1; mode=block");
                })
            )
            .csrf(withDefaults())

            // 2. 페이지 접근 권한 설정
            .authorizeHttpRequests(auth -> auth
                // (1) 메인 홈페이지 및 에러 페이지 허용
                .requestMatchers("/", "/index", "/error").permitAll()

                // (2) 회원가입, 로그인, 로그아웃 API 허용
                .requestMatchers("/join_new", "/api/members", "/join_end").permitAll()
                .requestMatchers("/login", "/api/login_check", "/api/logout").permitAll()

                // (3) 게시판 관련 기능 허용
                // (Controller에서 session check를 직접 수행하므로 Security는 통과시킴)
                .requestMatchers(
                        "/board_list", 
                        "/board_view/**", 
                        "/board_write", 
                        "/api/boards", 
                        "/board_edit/**", 
                        "/api/board_edit/**", 
                        "/api/board_delete/**"
                ).permitAll()

                // (4) 그 외 모든 요청은 로그인 필요
                .anyRequest().authenticated()
            )

            // 3. 세션 관리 (중복 로그인 방지)
            .sessionManagement(session -> session
                .invalidSessionUrl("/login")
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .expiredUrl("/login")
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}