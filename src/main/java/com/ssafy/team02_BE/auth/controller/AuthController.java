package com.ssafy.team02_BE.auth.controller;

import com.ssafy.team02_BE.auth.controller.dto.LoginRequestDTO;
import com.ssafy.team02_BE.auth.controller.dto.LoginResponseDTO;
import com.ssafy.team02_BE.auth.controller.dto.SignupRequestDTO;
import com.ssafy.team02_BE.auth.controller.dto.SignupResponseDTO;
import com.ssafy.team02_BE.auth.resolver.UserId;
import com.ssafy.team02_BE.auth.service.AuthService;
import com.ssafy.team02_BE.common.dto.ApiResponse;
import com.ssafy.team02_BE.exception.SuccessCode;
import com.ssafy.team02_BE.security.jwt.Jwt;
import com.ssafy.team02_BE.security.jwt.JwtProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "AuthController", description = "인증 관련 기능")
public class AuthController {
    private final AuthService authService;
    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    @Operation(summary = "로그인")
    private ResponseEntity<ApiResponse<LoginResponseDTO>> login(@RequestBody final LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        LoginResponseDTO loginUser = authService.login(loginRequestDTO);
        // loginUser로 token 생성
        Jwt jwt = authService.generateAuthTokens(loginUser.getId());
        // 생성한 token을 headers에 추가
        HttpHeaders headers = addJwtTokensToHeaders(jwt, response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(ApiResponse.success(
                        SuccessCode.LOGIN_SUCCESS,
                        loginUser
                ));
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletResponse response, @Parameter(hidden = true) @UserId Long userId) {
        // refreshToken 삭제
        authService.logout(userId);
        expireRefreshTokenCookie(response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        SuccessCode.LOGOUT_SUCCESS
                ));
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    private ResponseEntity<ApiResponse<SignupResponseDTO>> signup(@RequestBody SignupRequestDTO signupRequestDTO, HttpServletResponse response) {
        SignupResponseDTO signupUser = authService.signup(signupRequestDTO);
        // signupUser로 token 생성
        Jwt jwt = authService.generateAuthTokens(signupUser.getId());
        // 생성한 token을 headers에 추가
        HttpHeaders headers = addJwtTokensToHeaders(jwt, response);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(headers)
                .body(ApiResponse.success(
                        SuccessCode.SIGNUP_SUCCESS,
                        signupUser
                ));
    }

    @PostMapping("/reissue")
    @Operation(summary = "토큰 재발급")
    public ResponseEntity<ApiResponse<Void>> reissue(HttpServletRequest request, HttpServletResponse response) {
        // 쿠키에서 refreshToken 토큰 추출
        String refreshToken = extractRefreshTokenFromCookies(request);
        // refreshToken 유효성 검사 후 refreshToken과 accessToken 재발급
        Jwt jwt = authService.reissue(refreshToken);
        // 생성한 token을 headers에 추가
        HttpHeaders headers = addJwtTokensToHeaders(jwt, response);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(headers)
                .body(ApiResponse.success(
                        SuccessCode.TOKEN_REFRESH_SUCCESS
                ));
    }

    @DeleteMapping("/withdraw")
    @Operation(summary = "회원탈퇴")
    public ResponseEntity<ApiResponse<Void>> withdraw(HttpServletResponse response, @Parameter(hidden = true) @UserId Long userId) {
        // 회원탈퇴 처리 - User, refreshToken 삭제
        authService.withdraw(userId);
        // 쿠키에서 refresh 토큰 삭제
        expireRefreshTokenCookie(response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        SuccessCode.WITHDRAW_SUCCESS
                ));
    }

    private HttpHeaders addJwtTokensToHeaders(Jwt jwt, HttpServletResponse response) {
        // header에 accesstoken 저장
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt.accessToken());

        // Cookie에 refresh 토큰 저장
        Cookie refreshCookie = new Cookie("refresh", jwt.refreshToken());
        refreshCookie.setMaxAge(Math.toIntExact(jwtProvider.getRefreshTokenExpiration())); // 유효기간 7일
        refreshCookie.setPath("/api/auth"); // 쿠키는 /api/auth 경로에서만 유효함
        refreshCookie.setHttpOnly(true); // JS로 접근 불가 - XSS 공격 방지
        refreshCookie.setSecure(true); // HTTPS 연결에서만 전송
        refreshCookie.setAttribute("SameSite", "Strict"); // 같은 사이트에서만 전송 - CSRF 공격 방어
        response.addCookie(refreshCookie);

        return headers;
    }

    private void expireRefreshTokenCookie(HttpServletResponse response) {
        Cookie refreshCookie = new Cookie("refresh", null);
        refreshCookie.setMaxAge(0); // 즉시 만료
        refreshCookie.setPath("/api/auth");
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(true);
        response.addCookie(refreshCookie);
    }

    // 쿠키에서 refresh 토큰 추출
    public String extractRefreshTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refresh".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
