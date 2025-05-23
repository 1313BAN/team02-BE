package com.ssafy.team02_BE.security.jwt;

import com.ssafy.team02_BE.config.SecurityConfig;
import com.ssafy.team02_BE.exception.ErrorCode;
import com.ssafy.team02_BE.exception.model.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;


/**
 * 스프링 시큐리티 filter chain에 요청에 담긴 JWT를 검증하고 SecurityContextHolder에 세션을 생성
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        // 화이트리스트에 있는 경로는 토큰 검증 생략
        if (isWhiteListPath(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        // request에서 Authorization 헤더를 찾아 token을 꺼냄
        String accessToken = resolveAccessToken(request);

        // 보호된 경로인데 토큰이 없으면 예외 발생
        if (accessToken == null) {
            throw new UnauthorizedException(ErrorCode.NULL_TOKEN);
        }

        // accessToken 검증
        if (jwtProvider.validateToken(accessToken)) {
            //accessToken에서 꺼낸 UserId값을 바탕으로 authentication을 생성
            Authentication authentication = jwtProvider.getAuthenticationFromToken(accessToken);
            //스프링 시큐리티 인증 토큰을 SecurityContextHolder에 저장 (Controller에서 꺼내 쓰기 위함)
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private boolean isWhiteListPath(String requestURI) {
        return Arrays.stream(SecurityConfig.WHITE_LIST)
                .anyMatch(pattern -> pathMatcher.match(pattern, requestURI));
    }

    private String resolveAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(token -> token.substring(0, 7).equalsIgnoreCase("Bearer "))
                .map(token -> token.substring(7))
                .orElse(null);
    }
}