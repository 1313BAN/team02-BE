package com.ssafy.team02_BE.auth.controller.dto;

import com.ssafy.team02_BE.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    private Long id;
    private String email;
    private String nickname;

    public static LoginResponseDTO of(User user) {
        return new LoginResponseDTO(user.getId(), user.getEmail(), user.getNickname());
    }
}
