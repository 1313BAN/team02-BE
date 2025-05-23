package com.ssafy.team02_BE.auth.controller.dto;

import com.ssafy.team02_BE.auth.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupResponseDTO {
	private Long id;
	private String email;
	private String nickname;

	public static SignupResponseDTO of(User user) {
		return new SignupResponseDTO(user.getId(), user.getEmail(), user.getNickname());
	}
}