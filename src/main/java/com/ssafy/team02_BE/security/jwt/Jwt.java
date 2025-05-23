package com.ssafy.team02_BE.security.jwt;

import lombok.Builder;

@Builder
public record Jwt(
        String accessToken,
        String refreshToken
) {
}
