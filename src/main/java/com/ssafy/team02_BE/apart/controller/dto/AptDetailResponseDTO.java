package com.ssafy.team02_BE.apart.controller.dto;

import com.ssafy.team02_BE.apart.domain.ApartDeals;
import com.ssafy.team02_BE.apart.domain.ApartInfo;
import java.util.List;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class AptDetailResponseDTO {
	private String aptSeq;
	private ApartInfo apartInfo;
	private List<ApartDeals> apartDeals;
	public static AptDetailResponseDTO of(String aptSeq, ApartInfo info, List<ApartDeals> deals) {
		return AptDetailResponseDTO.builder()
			.aptSeq(aptSeq)
			.apartInfo(info)
			.apartDeals(deals)
			.build();
	}
}
