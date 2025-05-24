package com.ssafy.team02_BE.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {
    // auth
    SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입에 성공했습니다."),
    LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공했습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK, "로그아웃에 성공했습니다."),
    TOKEN_REFRESH_SUCCESS(HttpStatus.OK, "토큰 재발급에 성공했습니다."),
    WITHDRAW_SUCCESS(HttpStatus.OK, "탈퇴에 성공했습니다."),

    // apart
    GET_APARTINFO_BY_APTSEQ(HttpStatus.OK, "아파트 상세 정보 조회에 성공했습니다."),
    GET_APARTDEALS_BY_APTSEQ(HttpStatus.OK, "아파트 거래 조회에 성공했습니다."),
    GET_APARTINFOS_BY_DONGNAME(HttpStatus.OK, "동이름으로 아파트 검색에 성공했습니다."),
    GET_APARTDETAILS_BY_APTSEQ(HttpStatus.OK, "아파트 상세 정보와 거래 조회에 성공했습니다."),
    // user
//    NICKNAME_CHECK_SUCCESS(HttpStatus.OK, "닉네임 중복 확인에 성공했습니다."),
//    CHANGE_NICKNAME_SUCCESS(HttpStatus.OK, "닉네임 변경에 성공했습니다."),
//    GET_USERNAME_SUCCESS(HttpStatus.OK, "유저 이름 조회에 성공했습니다."),
    ;

    private final HttpStatus code;
    private final String message;
}
