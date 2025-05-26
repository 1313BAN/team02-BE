package com.ssafy.team02_BE.user.controller.dto;

import com.ssafy.team02_BE.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailResponseDTO {
    private Long id;
    private String email;
    private String nickname;
    private String name;
    private String phoneNumber;


    public static UserDetailResponseDTO of(User user) {
        return UserDetailResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
