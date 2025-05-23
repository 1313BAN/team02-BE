package com.ssafy.team02_BE.auth.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupRequestDTO {
	private String email;
	private String password;
	private String nickname;
}