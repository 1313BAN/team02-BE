package com.ssafy.team02_BE.user.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequestDTO {
    private String password;
    private String nickname;
    private String name;
    private String phoneNumber;

    public String getPassword() {
        return "{noop}" + password;
    }

    public boolean isNullPassword() {
        return password == null;
    }
}
