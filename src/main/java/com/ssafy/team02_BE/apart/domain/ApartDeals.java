package com.ssafy.team02_BE.apart.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
	indexes = {
		@Index(name = "idx_apt_seq", columnList = "aptSeq")
	}
)
public class ApartDeals {
	@Id
	private long no;
	private String aptSeq;
	private String aptDong;
	private String floor;
	private int dealYear;
	private int dealMonth;
	private int dealDay;
	private Double excluUseAr;
	private String dealAmount;
}
