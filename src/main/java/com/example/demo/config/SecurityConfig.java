package com.example.demo.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. 정적 리소스(이미지, CSS, JS, 라이브러리)는 보안 필터 제외 (가장 중요)
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico", "/error");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 2. CSRF 비활성화 (요청하신 부분)
            .csrf(csrf -> csrf.disable())

            // 3. 헤더 설정 (XSS 방지 등)
            .headers(headers -> headers
                .addHeaderWriter((request, response) -> {
                    response.setHeader("X-XSS-Protection", "1; mode=block");
                })
            )

            // 4. 경로별 접근 권한 설정
            .authorizeHttpRequests(auth -> auth
                // (1) 메인 홈페이지, 에러 페이지, 메일 업로드 기능 누구나 접근 가능
                .requestMatchers("/", "/index", "/error", "/upload-email", "/upload_end").permitAll()

                // (2) 회원가입, 로그인, 로그아웃 API 누구나 접근 가능
                .requestMatchers("/join_new", "/api/members", "/join_end").permitAll()
                .requestMatchers("/login", "/api/login_check", "/api/logout").permitAll()

                // (3) 게시판 기능은 Security에서 열어두고 Controller에서 세션 체크함
                .requestMatchers(
                        "/board_list", 
                        "/board_view/**", 
                        "/board_write", 
                        "/api/boards", 
                        "/board_edit/**", 
                        "/api/board_edit/**", 
                        "/api/board_delete/**"
                ).permitAll()

                // (4) 그 외 모든 페이지는 로그인 필요
                .anyRequest().authenticated()
            )

            // 5. 세션 관리 (중복 로그인 방지)
            .sessionManagement(session -> session
                .invalidSessionUrl("/login")       // 세션 끊기면 로그인으로
                .maximumSessions(1)                // 최대 1명 접속
                .maxSessionsPreventsLogin(true)    // 동시 접속 차단
                .expiredUrl("/login")
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}