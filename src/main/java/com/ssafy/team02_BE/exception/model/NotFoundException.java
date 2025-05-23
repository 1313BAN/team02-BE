package com.ssafy.team02_BE.exception.model;


import com.ssafy.team02_BE.exception.ErrorCode;

public class NotFoundException extends CustomException {
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
