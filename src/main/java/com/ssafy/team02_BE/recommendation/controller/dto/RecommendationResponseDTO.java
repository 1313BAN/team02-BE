package com.ssafy.team02_BE.recommendation.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationResponseDTO {

    private List<RecommendedNeighborhood> recommendations;
    private String analysisReason;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecommendedNeighborhood {
        private String dongName; // 동네 이름
        private String gugunName; // 구/군
        private String sidoName; // 시/도
        private Integer ranking; // 추천 순위
        private String summary; // 한줄 요약
        private String pros; // 장점
        private String cons; // 단점
        private String rentRange; // 예상 임대료 범위
        private String transportationInfo; // 교통 정보
        private String amenities; // 주변 편의시설
        private String atmosphere; // 동네 분위기
        private Double matchScore; // 매칭 점수 (0-100)
        private List<String> suitableFor; // 어떤 사람에게 적합한지
    }
}