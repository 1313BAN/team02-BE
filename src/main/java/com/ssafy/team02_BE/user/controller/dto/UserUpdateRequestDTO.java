package com.ssafy.team02_BE.user.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequestDTO {
    private String nickname;
    private String name;
    private String phoneNumber;
    private String password;

    public String getPassword() {
        return "{noop}" + password;
    }

    @JsonIgnore // Swagger와 JSON 직렬화에서 이 메서드를 숨김
    public boolean isNullPassword() {
        return password == null;
    }
}
