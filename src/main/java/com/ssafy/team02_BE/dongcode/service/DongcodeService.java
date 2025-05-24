package com.ssafy.team02_BE.dongcode.service;

import com.ssafy.team02_BE.dongcode.repository.DongcodeQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DongcodeService {
    private final DongcodeQueryRepository dongcodeQueryRepository;

    /**
     * sido 코드 조회
     */
    public List<String> getSidoList() {
        return dongcodeQueryRepository.findDistinctSidoList();
    }

    /**
     * gugun 코드 조회
     */
    public List<String> getGugunList(String sidoName) {
        return dongcodeQueryRepository.findDistinctGugunList(sidoName);
    }

    /**
     * dong 코드 조회
     */
    public List<String> getDongList(String sidoName, String gugunName) {
        return dongcodeQueryRepository.findDistinctDongList(sidoName, gugunName);
    }

//    /**
//     * sido 코드 조회
//     */
//    public SidoResponseDTO getSidoList() {
//        return SidoResponseDTO.of(dongcodeQueryRepository.findDistinctSidoList());
//    }
//
//    /**
//     * gugun 코드 조회
//     */
//    public SidoResponseDTO getGugunList(String sidoCode) {
//        String sidoName = dongcodeQueryRepository.findSidoNameBySidoCode(sidoCode);
//        if(sidoName == null) {
//            throw new BadRequestException(ErrorCode.UNREGISTERED_APART);
//        }
//        return SidoResponseDTO.of(dongcodeQueryRepository.findDistinctGugunList(sidoName));
//    }
//
//    /**
//     * dong 코드 조회
//     */
//    public SidoResponseDTO getDongCode(String sidoGugunCode) {
//        return SidoResponseDTO.of(dongcodeQueryRepository.findDistinctDongList(sidoGugunCode));
//    }

}