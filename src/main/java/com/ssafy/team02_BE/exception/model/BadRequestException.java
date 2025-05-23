package com.ssafy.team02_BE.exception.model;

import com.ssafy.team02_BE.exception.ErrorCode;

public class BadRequestException extends CustomException {
    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}