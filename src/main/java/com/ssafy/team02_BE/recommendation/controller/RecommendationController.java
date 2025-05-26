package com.ssafy.team02_BE.recommendation.controller;

import com.ssafy.team02_BE.common.dto.ApiResponse;
import com.ssafy.team02_BE.exception.SuccessCode;
import com.ssafy.team02_BE.recommendation.controller.dto.RecommendationRequestDTO;
import com.ssafy.team02_BE.recommendation.controller.dto.RecommendationResponseDTO;
import com.ssafy.team02_BE.recommendation.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recommendation")
@RequiredArgsConstructor
@Tag(name = "RecommendationController", description = "동네 추천 관련 기능")
public class RecommendationController {

    private final RecommendationService recommendationService;

    /**
     * 동네 추천 API
     * 사용자의 조건을 바탕으로 적합한 동네를 AI가 추천해줍니다.
     *
     * @param request 사용자의 동네 선택 조건
     * @return 추천 동네 목록 및 분석 결과
     */
    @PostMapping("/recommend")
    @Operation(summary = "AI 동네 추천")
    public ResponseEntity<ApiResponse<RecommendationResponseDTO>> getRecommendNeighborhoods(@RequestBody RecommendationRequestDTO request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        SuccessCode.GET_RECOMMEND_NEIGHBORHOODS,
                        recommendationService.getRecommendNeighborhoods(request)
                ));
    }
}
