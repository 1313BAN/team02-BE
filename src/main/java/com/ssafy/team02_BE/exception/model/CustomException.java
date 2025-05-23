package com.ssafy.team02_BE.exception.model;

import com.ssafy.team02_BE.exception.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
