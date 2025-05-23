package com.ssafy.team02_BE.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.team02_BE.common.dto.ApiResponse;
import com.ssafy.team02_BE.exception.ErrorCode;
import com.ssafy.team02_BE.exception.model.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.ssafy.team02_BE.exception.ErrorCode.*;


/**
 * JWT 관련 예외를 처리하는 전역 예외 필터
 * OncePerRequestFilter: 요청당 한 번만 동작하는 커스텀 필터
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    // spring-boot-starter-web 의존성을 포함하면 자동 빈 주입
    private final ObjectMapper objectMapper;

    /**
     * FilterChain 내에서 발생한 예외만 잡음
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (UnauthorizedException e) {
            if (e.getErrorCode().equals(EXPIRED_TOKEN)) {
                setErrorResponse(response, EXPIRED_TOKEN);
            } else if (e.getErrorCode().equals(MALFORMED_TOKEN)) {
                setErrorResponse(response, MALFORMED_TOKEN);
            } else if (e.getErrorCode().equals(NULL_TOKEN)) {
                setErrorResponse(response, NULL_TOKEN);
            }
        }
    }

    /**
     * Filter는 DispatcherServlet보다 먼저 동작하는 계층이기 때문에,
     * ControllerExceptionAdvice가 받을 수 없음!
     * 따라서 Filter에서 발생한 예외는 자체적으로 응답을 만들고 끝내야 함
     */
    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorCode.getCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try {
            response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.error(errorCode)));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}