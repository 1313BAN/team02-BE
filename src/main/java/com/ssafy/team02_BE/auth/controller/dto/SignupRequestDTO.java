package com.ssafy.team02_BE.auth.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDTO {
	private String email;
	private String password;
	private String nickname;
}