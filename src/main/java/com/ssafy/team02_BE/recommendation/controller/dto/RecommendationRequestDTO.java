package com.ssafy.team02_BE.recommendation.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationRequestDTO {

//    @NotBlank(message = "지역은 필수입니다")
//    private String region; // 서울, 부산, 대구 등

    @Min(value = 0, message = "예산은 0 이상이어야 합니다")
    private Long minBudget; // 최소 예산 (만원)

    @Min(value = 0, message = "예산은 0 이상이어야 합니다")
    private Long maxBudget; // 최대 예산 (만원)

    private String houseType; // 원룸, 투룸, 오피스텔, 아파트 등

    @Min(value = 1, message = "나이는 1 이상이어야 합니다")
    @Max(value = 100, message = "나이는 100 이하여야 합니다")
    private Integer age; // 나이

    private String job; // 직업 (학생, 직장인, 프리랜서 등)

    private String lifestyle; // 라이프스타일 (집순이, 외향적, 운동좋아함, 카페/술집, 쇼핑 등)

    private String transport; // 주요 교통수단 (지하철, 버스, 자차, 도보 등)

    private String familyStatus; // 가족상황 (1인가구, 신혼부부, 가족 등)

    private String neighborhoodMood;
}