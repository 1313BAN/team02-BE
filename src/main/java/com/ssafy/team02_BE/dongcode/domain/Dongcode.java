package com.ssafy.team02_BE.dongcode.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dongcode {
	@Id
	private String dongCode;
	private String sidoName;
	private String gugunName;
	private String dongName;

}
