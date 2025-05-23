package com.ssafy.team02_BE.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // common
    NOT_EXIST_PARAMETER(HttpStatus.BAD_REQUEST, "필수 파라미터 값이 없습니다."),
    NOT_SUPPORTED_METHOD(HttpStatus.METHOD_NOT_ALLOWED, "사용할 수 없는 HTTP method입니다."),
    INVALID_REQUEST_BODY(HttpStatus.BAD_REQUEST, "잘못된 Request body입니다."),
    BAD_REQUEST_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 형식의 요청입니다."),
    FILE_SAVE_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "파일 생성에 실패했습니다."),
    FORBIDDEN_EXCEPTION(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    INVALID_PLATFORM(HttpStatus.BAD_REQUEST, "허용되지 않은 플랫폼입니다."),

    // auth
    UNREGISTERED_USER(HttpStatus.NOT_FOUND, "등록되지 않은 사용자입니다."),
    BAD_CREDENTIALS(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    NOT_LOGGED_IN_USER(HttpStatus.NOT_FOUND, "로그인되지 않은 사용자입니다."),
    ALREADY_EXISTS_USER(HttpStatus.CONFLICT, "이미 회원가입이 완료된 사용자입니다."),
    ALREADY_USING_NICKNAME(HttpStatus.CONFLICT, "이미 사용중인 닉네임입니다."),

    // jwt
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 형식의 토큰입니다."),
    NULL_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 존재하지 않습니다."),
    MISSING_CLAIM_TOKEN(HttpStatus.UNAUTHORIZED, "정보가 누락된 토큰입니다."),
    INVALID_TOKEN_PAYLOAD(HttpStatus.UNAUTHORIZED, "토큰 payload가 유효하지 않습니다."),
    UNAUTHORIZED_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    ;

    private final HttpStatus code;
    private final String message;
}
