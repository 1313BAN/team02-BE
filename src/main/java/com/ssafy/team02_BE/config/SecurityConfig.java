package com.ssafy.team02_BE.config;

import com.ssafy.team02_BE.security.filter.JwtExceptionHandlerFilter;
import com.ssafy.team02_BE.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final JwtExceptionHandlerFilter jwtExceptionHandlerFilter;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    // 인증 없이 접근 가능한 경로
    public static final String[] WHITE_LIST = {
            // swagger
            "/v3/**",
            "/swagger-ui/**",
            "/swagger-ui.html",

            // api
            "/auth/signup",
            "/auth/login",
            "/auth/reissue",
            "/map/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // csrf disable
                .csrf(AbstractHttpConfigurer::disable)

                //cors
                .cors(Customizer.withDefaults())

                // 클릭재킹 공격 방어
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))

                // form 로그인 방식 disable
                .formLogin(AbstractHttpConfigurer::disable)

                // 세션 정책 설정 - JWT 사용시 STATELESS
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 경로별 인가 작업
                .authorizeHttpRequests(request -> request
                        .requestMatchers(WHITE_LIST).permitAll()
                        .anyRequest().authenticated()
                )

                // Jwt 필터 등록 ExceptionHandlerFilter -> JwtFilter
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilterBefore(
                        jwtExceptionHandlerFilter,
                        JwtFilter.class
                )
        ;

        return http.build();
    }
}
