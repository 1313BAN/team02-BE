package com.ssafy.team02_BE.exception.model;

import com.ssafy.team02_BE.exception.ErrorCode;

public class UnauthorizedException extends CustomException {
    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}