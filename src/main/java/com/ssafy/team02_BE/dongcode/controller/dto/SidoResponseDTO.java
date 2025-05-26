package com.ssafy.team02_BE.dongcode.controller.dto;

import java.util.List;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class SidoResponseDTO {
    private InnerAdmVOList admVOList;

    @Getter
    @Builder
    public static class InnerAdmVOList {
        private int pageNo;
        private List<AdmVO> admVOList;
    }

    @Getter
    @NoArgsConstructor  // 기본 생성자 추가
    @AllArgsConstructor // 모든 필드 생성자 추가 (중요!)
    @Builder
    public static class AdmVO {
        private String admCode;
        private String admCodeNm;
        private String lowestAdmCodeNm;
    }

    public static SidoResponseDTO of(List<AdmVO> admVOList) {
        return SidoResponseDTO.builder()
            .admVOList(InnerAdmVOList.builder().pageNo(1).admVOList(admVOList).build())
            .build();
    }
}