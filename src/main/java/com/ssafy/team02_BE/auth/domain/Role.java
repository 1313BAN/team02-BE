package com.ssafy.team02_BE.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
/**
 * 권한
 */
public enum Role {

    USER("사용자")
    ;

    private final String title;
}