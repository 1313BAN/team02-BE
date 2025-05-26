package com.ssafy.team02_BE.auth.controller.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class SignupRequestDTO {
	private String email;
	private String password;
	private String nickname;
}