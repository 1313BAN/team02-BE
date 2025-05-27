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
        private String id;
        private String sido; // 동네 이름
        private String gungu; // 구/군
        private String dong; // 시/도
        private String score; // 추천 순위
        private String reason; // 한줄 요약
        private List<String> tags;
    }
}