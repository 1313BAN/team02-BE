package com.ssafy.team02_BE.apart.service;

import com.ssafy.team02_BE.apart.domain.ApartInfo;
import com.ssafy.team02_BE.apart.repository.ApartInfoRepository;
import com.ssafy.team02_BE.exception.ErrorCode;
import com.ssafy.team02_BE.exception.model.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
// JPA에서 쓰기 지연 저장소를 비활성화하고, 변경 감지 기능을 끔 TODO; 추가 공부
@Transactional(readOnly = true)
public class ApartInfoService {
	private final ApartInfoRepository apartInfoRepository;

	public Boolean existsByAptSeq(String aptSeq) {
		if(!apartInfoRepository.existsByAptSeq(aptSeq))
			throw new NotFoundException(ErrorCode.UNREGISTERED_APART);
		return true;
	}

	// 주택 상세 정보 조회
	public ApartInfo getApartInfoByAptSeq(String aptSeq) throws Exception {
		return apartInfoRepository.findByAptSeq(aptSeq);
	}

//	// 동이름으로 주택 리스트 조회
//	public List<HouseInfoDealsDTO> getHouseListByDongName(String dongName) throws Exception {
//		List<HouseInfoDealsDTO> li = apartInfoRepository.getHouseListByDongName(dongName);
//		MergeSorter.mergeSort(li,
//				(a, b) -> Integer.compare(b.getHouseInfoDTO().getBuildYear(), a.getHouseInfoDTO().getBuildYear()));
//		return li;
//	}

}