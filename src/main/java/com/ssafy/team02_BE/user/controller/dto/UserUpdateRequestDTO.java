package com.ssafy.team02_BE.user.controller.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class UserUpdateRequestDTO {
    private String password;
    private String nickname;
}
