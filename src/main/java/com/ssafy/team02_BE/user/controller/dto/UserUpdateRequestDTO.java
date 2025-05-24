package com.ssafy.team02_BE.user.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserUpdateRequestDTO {
    private String password;
    private String nickname;
}
