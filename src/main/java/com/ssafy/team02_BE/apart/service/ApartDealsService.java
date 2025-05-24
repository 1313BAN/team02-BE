package com.ssafy.team02_BE.apart.service;

import com.ssafy.team02_BE.apart.domain.ApartDeals;
import com.ssafy.team02_BE.apart.domain.ApartInfo;
import com.ssafy.team02_BE.apart.repository.ApartDealsRepository;
import com.ssafy.team02_BE.apart.repository.ApartInfoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApartDealsService {
	private final ApartInfoService apartInfoService;
	private final ApartDealsRepository apartDealsRepository;

	/**
	 * aptSeq로 아파트 거래 조회
	 */
	public List<ApartDeals> getApartDealsByAptSeq(String aptSeq) throws Exception {
		// 해당 아파트가 존재하는지 여부
		apartInfoService.existsByAptSeq(aptSeq);
		return apartDealsRepository.findByAptSeq(aptSeq);
	}
}