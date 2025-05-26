package com.ssafy.team02_BE.common.dto;

import com.ssafy.team02_BE.exception.ErrorCode;
import com.ssafy.team02_BE.exception.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
public class ApiResponse<T> { //모든 API 응답 형식을 통일함
    private final int code;
    private final String message;
    private T data;

    public static <T> ApiResponse<T> success(SuccessCode successCode) {
        return new ApiResponse<>(successCode.getCode().value(), successCode.getMessage(), null);
    }

    public static <T> ApiResponse<T> success(SuccessCode successCode, T data) {
        return new ApiResponse<>(successCode.getCode().value(), successCode.getMessage(), data);
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getCode().value(), errorCode.getMessage(), null);
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode, String message) {
        return new ApiResponse<>(errorCode.getCode().value(), message, null);
    }
}