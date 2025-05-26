package com.ssafy.team02_BE.apart.service;

import com.ssafy.team02_BE.apart.controller.dto.AptDetailResponseDTO;
import com.ssafy.team02_BE.apart.domain.ApartDeals;
import com.ssafy.team02_BE.apart.domain.ApartInfo;
import com.ssafy.team02_BE.apart.repository.ApartDealsRepository;
import com.ssafy.team02_BE.apart.repository.ApartInfoRepository;
import com.ssafy.team02_BE.apart.repository.ApartQueryRepository;
import com.ssafy.team02_BE.exception.ErrorCode;
import com.ssafy.team02_BE.exception.model.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
// JPA에서 쓰기 지연 저장소를 비활성화하고, 변경 감지 기능을 끔 TODO; 추가 공부
@Transactional(readOnly = true)
public class ApartInfoService {
    private final ApartInfoRepository apartInfoRepository;
    private final ApartQueryRepository apartQueryRepository;
    private final ApartDealsRepository apartDealsRepository;

    /**
     * AptSeq로 아파트 존재 여부 확인
     */
    public Boolean existsByAptSeq(String aptSeq) {
        if (!apartInfoRepository.existsByAptSeq(aptSeq))
            throw new NotFoundException(ErrorCode.UNREGISTERED_APART);
        return true;
    }

    /**
     * AptSeq로 아파트 상세 정보 조회
     */
    public ApartInfo getApartInfoByAptSeq(String aptSeq) {
        existsByAptSeq(aptSeq); // 존재 여부 검증
        return apartInfoRepository.findByAptSeq(aptSeq).get();
    }

    /**
     * 동이름으로 아파트 상세 정보 리스트 조회
     */
    public List<ApartInfo> getApartInfosByDongName(String sidoName, String gugunName, String dongName) {
        return apartQueryRepository.getApartInfosByDongName(sidoName, gugunName, dongName);
    }

    /**
     * AptSeq로 아파트 상세(아파트 상세 정보 + 아파트 거래 정보 리스트) 조회
     */
    public AptDetailResponseDTO getApartDetailsByAptSeq(String aptSeq) throws Exception {
        existsByAptSeq(aptSeq); // 존재 여부 검증
        ApartInfo apartInfo = getApartInfoByAptSeq(aptSeq);

        // aptSeq로 최신순 100개의 거래량 조회
        List<ApartDeals> deals = apartDealsRepository.findTop100ByAptSeqOrderByDealYearDescDealMonthDesc(aptSeq);

        return AptDetailResponseDTO.of(aptSeq, apartInfo, deals);
    }

}