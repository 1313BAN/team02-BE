package com.ssafy.team02_BE.user.controller.dto;

import com.ssafy.team02_BE.user.domain.User;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class UserDetailResponseDTO {
    private Long id;
    private String email;
    private String nickname;

    public static UserDetailResponseDTO of(User user) {
        return UserDetailResponseDTO.builder()
            .id(user.getId())
            .email(user.getEmail())
            .nickname(user.getNickname())
            .build();
    }
}
