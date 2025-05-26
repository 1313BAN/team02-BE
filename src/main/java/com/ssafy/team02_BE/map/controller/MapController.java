package com.ssafy.team02_BE.map.controller;

import com.ssafy.team02_BE.common.dto.ApiResponse;
import com.ssafy.team02_BE.exception.SuccessCode;
import com.ssafy.team02_BE.map.controller.dto.ApartCoordRequestDTO;
import com.ssafy.team02_BE.map.service.ApartCoordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/map")
@RequiredArgsConstructor
@Tag(name = "MapController", description = "동네 추천 관련 기능")
public class MapController {

    private final ApartCoordService mapService;

    public Map<String, Object> getAccessToken() {
        return (Map<String, Object>) WebClient.builder()
                .baseUrl("https://sgisapi.kostat.go.kr")
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/OpenAPI3/auth/authentication.json")
                        .queryParam("consumer_key", "1cf2dad894ad48d79456")
                        .queryParam("consumer_secret", "8d956a4da0c24e8bb8ec")
                        .build())
                .retrieve()
                .bodyToMono(Map.class)
                .block().get("result"); // 동기 방식
    }

    @GetMapping("/coords")
    @ResponseBody
    public Map<String, Object> getCoords(@RequestParam String address) {
        String accessToken = getAccessToken().get("accessToken").toString();

        Map<String, Object> response = WebClient.builder()
                .baseUrl("https://sgisapi.kostat.go.kr")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/OpenAPI3/addr/geocode.json")
                        .queryParam("accessToken", accessToken)
                        .queryParam("address", address)
                        .queryParam("resultcount", 1)
                        .build())
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        // 예외 처리 및 토큰 재발급 로직
        if (response != null && Integer.valueOf(-401).equals(response.get("errCd"))) {
            // accessToken 재발급 후 재시도
            String newAccessToken = getAccessToken().get("accessToken").toString();

            response = WebClient.builder()
                    .baseUrl("https://sgisapi.kostat.go.kr")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build()
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/OpenAPI3/addr/geocode.json")
                            .queryParam("accessToken", newAccessToken)
                            .queryParam("address", address)
                            .queryParam("resultcount", 1)
                            .build())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        }

        // 최종 좌표 추출
        if (response != null && response.containsKey("result")) {
            Map<String, Object> result = (Map<String, Object>) response.get("result");
            List<Map<String, Object>> resultdata = (List<Map<String, Object>>) result.get("resultdata");

            if (resultdata != null && !resultdata.isEmpty()) {
                return resultdata.get(0); // x, y 좌표 반환
            }
        }

        return Collections.emptyMap(); // 실패 시 빈 맵 반환
    }

    @PostMapping("/coord")
    @Operation(summary = "aptCoord 저장")
    public ResponseEntity<ApiResponse<Void>> insertApartCoords(@RequestBody ApartCoordRequestDTO apartCoordRequestDTO) throws Exception {
        mapService.insertApartCoords(apartCoordRequestDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        SuccessCode.POST_APARTCOORDS
                ));
    }


}