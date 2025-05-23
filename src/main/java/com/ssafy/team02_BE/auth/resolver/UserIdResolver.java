package com.ssafy.team02_BE.auth.resolver;

import com.ssafy.team02_BE.exception.ErrorCode;
import com.ssafy.team02_BE.exception.model.UnauthorizedException;
import com.ssafy.team02_BE.security.jwt.JwtProvider;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


/**
 * 컨트롤러 메서드에서 특정 조건에 맞는 파라미터가 있을 때 원하는 값을 바인딩해주는 인터페이스
 *
 * @UserId -> resolveArgument() 반환값을 바인딩
 */
@RequiredArgsConstructor
@Component
public class UserIdResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;

    /**
     * 핸들러(컨트롤러 메소드)의 특정 파라미터를 지원하는지 여부를 판단하기 위한 메소드
     * 어떤 파라미터에 대한 작업을 수행할 것인지 지정
     *
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserId.class) && Long.class.equals(parameter.getParameterType());
    }

    /**
     * 파라미터에 대한 로직 수행 (parameter 값에 바인딩할 값 처리)
     */
    @Override
    public Object resolveArgument(@NotNull MethodParameter parameter, ModelAndViewContainer modelAndViewContainer, @NotNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        // SecurityContext에서 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증되지 않았다면 예외 처리
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException(ErrorCode.UNAUTHORIZED_TOKEN);
        }

        // Authentication에서 userId를 추출 -> Controller의 파라미터에 자동으로 주입됨
        return jwtProvider.extractUserIdFromAuthentication(authentication);
    }
}
