package com.ssafy.team02_BE.recommendation.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.team02_BE.recommendation.controller.dto.RecommendationRequestDTO;
import com.ssafy.team02_BE.recommendation.controller.dto.RecommendationResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final ChatModel chatModel;
    private final ObjectMapper objectMapper;

    public RecommendationResponseDTO getRecommendNeighborhoods(RecommendationRequestDTO request) {
        try {
            String promptContent = generateDetailedPrompt(request);

            PromptTemplate promptTemplate = new PromptTemplate(promptContent);
            Prompt prompt = promptTemplate.create();

            var result = chatModel.call(prompt);
            String aiResponse = result.getResult().getOutput().getText();

//            System.out.println("AI response: " + aiResponse);

            return parseAiResponse(aiResponse, request);

        } catch (Exception e) {
            log.error("동네 추천 생성 중 오류 발생", e);
            return createFallbackResponse(request);
        }
    }

    private String generateDetailedPrompt(RecommendationRequestDTO request) {
        Map<String, Object> promptData = new HashMap<>();

        // 기본 정보
//        promptData.put("region", request.getRegion());
        promptData.put("minBudget", request.getMinBudget() != null ? request.getMinBudget() : "제한없음");
        promptData.put("maxBudget", request.getMaxBudget() != null ? request.getMaxBudget() : "제한없음");
        promptData.put("housingType", request.getHousingType() != null ? request.getHousingType() : "상관없음");
        promptData.put("age", request.getAge() != null ? request.getAge() : "미제공");
        promptData.put("occupation", request.getOccupation() != null ? request.getOccupation() : "미제공");

        promptData.put("lifestyle", request.getLifestyle() != null ? request.getLifestyle() : "일반적인 생활");
        promptData.put("transportation", request.getTransportation() != null ? request.getTransportation() : "대중교통");
        promptData.put("familyStatus", request.getFamilyStatus() != null ? request.getFamilyStatus() : "미제공");

        String templateString = """
                당신은 대한민국 부동산 및 지역 전문가입니다. 전국에 존재하는 읍/면/동 중 아래에 사용자 정보와 가장 일치하는 동네 3를 추천하세요.
                
                # 사용자 정보
                        - 희망 지역: <region>
                        - 예산: <minBudget>만원 ~ <maxBudget>만원
                        - 선호 주거형태: <housingType>
                        - 나이: <age>세
                        - 직장/학교 위치: <workLocation>
                        - 라이프스타일: <lifestyle>
                        - 주요 교통수단: <transportation>
                        - 가족상황: <familyStatus>
                
                # 응답 형식
                        - 반드시 유효한 JSON만 반환
                                - recommendations 배열에는 <region> 내의 실제 읍/면/동만 포함
                                - 각 동네마다 dongName(동네명), gugunName(구/군명), sidoName(시도명), ranking(순위), summary(한줄요약), pros, cons, rentRange, transportationInfo, amenities, atmosphere, suitableFor 포함
                                - analysisReason으로 전체 추천 근거 설명
                ## 추천 기준 및 가이드라인
                                1. **정확성**: 실제 존재하는 동네만 추천하고, 현실적인 임대료 정보를 제공하세요.
                                2. **개인화**: 사용자의 나이, 직업, 라이프스타일을 충분히 고려하여 맞춤형 추천을 하세요.
                                3. **다양성**: 서로 다른 특성을 가진 3개의 동네를 추천하여 선택의 폭을 넓혀주세요.
                                4. **실용성**: 출퇴근 편의성, 생활 편의시설, 치안, 주거환경 등을 종합적으로 고려하세요.
                                5. **균형성**: 장점뿐만 아니라 단점이나 주의사항도 솔직하게 제시하세요.
                                6. **구체성**: 지하철 몇 호선, 몇 분 거리, 구체적인 편의시설명 등을 포함하세요.
                # 추가 조건
                    만족하는 3개의 동네가 없을 경우 서울 강남구의 3개의 동네를 추천하세요.
                
                """;

        PromptTemplate promptTemplate = new PromptTemplate(templateString);
        return promptTemplate.create(promptData).getContents();
    }

//        private NeighborhoodResponseDTO parseAiResponse2(String aiResponse, NeighborhoodRequestDTO request) {
//        try {
//            // JSON 부분만 추출 (```json으로 감싸져 있을 수 있음)
//            String jsonResponse = extractJsonFromResponse(aiResponse);
//
//            // JSON을 DTO로 변환
//            return objectMapper.readValue(jsonResponse, NeighborhoodResponseDTO.class);
//
//        } catch (JsonProcessingException e) {
//            log.warn("AI 응답 JSON 파싱 실패, 대체 응답 생성: {}", e.getMessage());
//            return createFallbackResponse(request);
//        }
//    }
    private RecommendationResponseDTO parseAiResponse(String aiResponse, RecommendationRequestDTO request) {
        try {
            // JSON 부분만 추출
            String jsonResponse = extractJsonFromResponse(aiResponse);

            // JsonNode로 먼저 파싱하여 구조 확인
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            // recommendations 배열 처리
            JsonNode recommendationsNode = rootNode.get("recommendations");
            List<RecommendationResponseDTO.RecommendedNeighborhood> neighborhoods = new ArrayList<>();

            if (recommendationsNode != null && recommendationsNode.isArray()) {
                for (JsonNode neighborhoodNode : recommendationsNode) {
                    RecommendationResponseDTO.RecommendedNeighborhood.RecommendedNeighborhoodBuilder builder =
                            RecommendationResponseDTO.RecommendedNeighborhood.builder();

                    // 기본 필드들
                    builder.dongName(getStringValue(neighborhoodNode, "dongName"));
                    builder.gugunName(getStringValue(neighborhoodNode, "gugunName"));
                    builder.sidoName(getStringValue(neighborhoodNode, "sidoName"));
                    builder.ranking(getIntValue(neighborhoodNode, "ranking"));
                    builder.summary(getStringValue(neighborhoodNode, "summary"));
                    builder.rentRange(getStringValue(neighborhoodNode, "rentRange"));
                    builder.transportationInfo(getStringValue(neighborhoodNode, "transportationInfo"));
                    builder.amenities(getStringValue(neighborhoodNode, "amenities"));
                    builder.atmosphere(getStringValue(neighborhoodNode, "atmosphere"));
                    builder.matchScore(getDoubleValue(neighborhoodNode, "matchScore"));

                    // pros 처리 (배열 또는 문자열 모두 처리)
                    builder.pros(convertToString(neighborhoodNode.get("pros")));

                    // cons 처리 (배열 또는 문자열 모두 처리)
                    builder.cons(convertToString(neighborhoodNode.get("cons")));

                    // suitableFor 처리 (배열을 List<String>으로)
                    List<String> suitableFor = new ArrayList<>();
                    JsonNode suitableForNode = neighborhoodNode.get("suitableFor");
                    if (suitableForNode != null && suitableForNode.isArray()) {
                        for (JsonNode item : suitableForNode) {
                            suitableFor.add(item.asText());
                        }
                    }
                    builder.suitableFor(suitableFor);

                    neighborhoods.add(builder.build());
                }
            }

            return RecommendationResponseDTO.builder()
                    .recommendations(neighborhoods)
                    .analysisReason(getStringValue(rootNode, "analysisReason"))
                    .build();

        } catch (Exception e) {
            log.warn("AI 응답 JSON 파싱 실패, 대체 응답 생성: {}", e.getMessage());
            return createFallbackResponse(request);
        }
    }

    private String getStringValue(JsonNode node, String fieldName) {
        JsonNode fieldNode = node.get(fieldName);
        return fieldNode != null ? fieldNode.asText() : null;
    }

    private Integer getIntValue(JsonNode node, String fieldName) {
        JsonNode fieldNode = node.get(fieldName);
        return fieldNode != null ? fieldNode.asInt() : null;
    }

    private Double getDoubleValue(JsonNode node, String fieldName) {
        JsonNode fieldNode = node.get(fieldName);
        return fieldNode != null ? fieldNode.asDouble() : null;
    }

    private String convertToString(JsonNode node) {
        if (node == null) return null;

        if (node.isArray()) {
            // 배열인 경우 쉼표로 연결
            List<String> items = new ArrayList<>();
            for (JsonNode item : node) {
                items.add(item.asText());
            }
            return String.join(", ", items);
        } else {
            // 문자열인 경우 그대로 반환
            return node.asText();
        }
    }

    private String extractJsonFromResponse(String response) {
        // ```json으로 감싸진 경우 추출
        if (response.contains("```json")) {
            int startIndex = response.indexOf("```json") + 7;
            int endIndex = response.lastIndexOf("```");
            if (endIndex > startIndex) {
                return response.substring(startIndex, endIndex).trim();
            }
        }

        // JSON 객체 시작/끝 브레이스 찾기
        int startBrace = response.indexOf("{");
        int endBrace = response.lastIndexOf("}");

        if (startBrace != -1 && endBrace != -1 && endBrace > startBrace) {
            return response.substring(startBrace, endBrace + 1);
        }

        return response.trim();
    }

    private RecommendationResponseDTO createFallbackResponse(RecommendationRequestDTO request) {
        // AI 응답 실패 시 기본 응답 생성
        List<RecommendationResponseDTO.RecommendedNeighborhood> fallbackRecommendations = Arrays.asList(
                RecommendationResponseDTO.RecommendedNeighborhood.builder()
                        .dongName("홍대")
                        .gugunName("마포구")
                        .sidoName("서울특별시")
                        .ranking(1)
                        .summary("젊고 활기찬 문화의 중심지")
                        .pros("뛰어난 대중교통 접근성, 다양한 문화생활, 풍부한 먹거리")
                        .cons("소음이 많고 임대료가 높은 편")
                        .rentRange("원룸 50-80만원, 투룸 80-120만원")
                        .transportationInfo("지하철 2,6,9호선, 공항철도 이용 가능")
                        .amenities("대형마트, 병원, 카페, 음식점 다수")
                        .atmosphere("젊고 역동적인 분위기, 24시간 활기참")
                        .matchScore(85.0)
                        .suitableFor(Arrays.asList("20-30대", "문화생활 선호", "대중교통 이용자"))
                        .build()
        );

        return RecommendationResponseDTO.builder()
                .recommendations(fallbackRecommendations)
                .analysisReason("시스템 오류로 인한 기본 추천입니다. 더 정확한 추천을 위해 다시 시도해주세요.")
                .build();
    }
}