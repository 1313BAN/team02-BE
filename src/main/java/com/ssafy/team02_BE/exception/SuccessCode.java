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

    // dongcode
    GET_SIDOS(HttpStatus.OK, "시도 조회에 성공했습니다."),
    GET_GUGUNS(HttpStatus.OK, "구군 조회에 성공했습니다."),
    GET_DONGS(HttpStatus.OK, "동 조회에 성공했습니다."),

    // user
    UPDATE_USER_SUCCESS(HttpStatus.OK, "유저 정보 수정에 성공했습니다."),
    GET_USER_SUCCESS(HttpStatus.OK, "유저 정보 조회에 성공했습니다."),

    // like
    POST_LIKE_SUCCESS(HttpStatus.CREATED, "매물 찜하기에 성공했습니다."),
    DELETE_LIKE_SUCCESS(HttpStatus.OK, "매물 찜 취소에 성공했습니다."),
    GET_LIKES_SUCCESS(HttpStatus.OK, "내가 찜한 매물 조회에 성공했습니다."),
    ;

    private final HttpStatus code;
    private final String message;
}
