package com.ssafy.team02_BE.exception.model;

import com.ssafy.team02_BE.exception.ErrorCode;

public class ConflictException extends CustomException {
    public ConflictException(ErrorCode errorCode) {
        super(errorCode);
    }
}