package com.ssafy.team02_BE.dongcode.controller;

import com.ssafy.team02_BE.common.dto.ApiResponse;
import com.ssafy.team02_BE.dongcode.service.DongcodeService;
import com.ssafy.team02_BE.exception.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/map/dongcode")
@Tag(name = "DongcodeController", description = "동코드 관련 기능")
public class DongcodeController {
    private final DongcodeService dongcodeService;
//    @Value("${key.vworld}")
//    private String vworld;
//    @Value("${key.sgis.service.id}")
//    private String serviceId;
//    @Value("${key.sgis.security}")
//    private String security;
//    @Value("${key.data}")
//    private String data;

    @GetMapping("/sido")
    @Operation(summary = "시도명 조회")
    public ResponseEntity<ApiResponse<List<String>>> getSidoList() {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(
                SuccessCode.GET_SIDOS,
                dongcodeService.getSidoList()
            ));
    }

    @GetMapping("/gugun")
    @Operation(summary = "구군명 조회")
    public ResponseEntity<ApiResponse<List<String>>> getGugunList(@RequestParam String sidoName) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(
                SuccessCode.GET_GUGUNS,
                dongcodeService.getGugunList(sidoName)
            ));
    }

    @GetMapping("/dong")
    @Operation(summary = "동이름 조회")
    public ResponseEntity<ApiResponse<List<String>>> getDongList(@RequestParam String sidoName, @RequestParam String gugunName) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(
                SuccessCode.GET_DONGS,
                dongcodeService.getDongList(sidoName, gugunName)
            ));
    }

//    @GetMapping("/sido")
//    @Operation(summary = "sido 코드 조회")
//    public ResponseEntity<SidoResponseDTO> getSidoList() {
//        return ResponseEntity.ok(dongcodeService.getSidoList());
//    }
//
//    @GetMapping("/gugun")
//    @Operation(summary = "gugun 코드 조회")
//    public ResponseEntity<SidoResponseDTO> getGugunList(@RequestParam String sidoCode) {
//        return ResponseEntity.ok(dongcodeService.getGugunList(sidoCode));
//    }
//
//    @GetMapping("/dong")
//    @Operation(summary = "dong 코드 조회")
//    public ResponseEntity<SidoResponseDTO> getDongCode(@RequestParam String sidoGugunCode) {
//        return ResponseEntity.ok(dongcodeService.getDongCode(sidoGugunCode));
//    }


//    @GetMapping("/admCode") //시도 코드
//    @ResponseBody
//    public Map<String, Object> getAdmCodeList() { //String keyVWorld
//        return WebClient.builder().baseUrl("https://api.vworld.kr").build()
//            .get()
//            .uri(uriBuilder -> uriBuilder
//                .path("/ned/data/admCodeList")
//                .queryParam("format", "json")
//                .queryParam("numOfRows", 100)
//                .queryParam("key", vworld)  //String keyVWorld
//                .queryParam("domain", "localhost")
//                .build())
//            .retrieve()
//            .bodyToMono(Map.class) // 응답을 Map 형태로 받음
//            .block(); // 동기 방식으로 기다림
//    }
//
//    @GetMapping("/si")
//    @ResponseBody
//    public Map<String, Object> getAdmSiList(@RequestParam("sido") String sido) { //String keyVWorld
//        return WebClient.builder().baseUrl("https://api.vworld.kr").build()
//            .get()
//            .uri(uriBuilder -> uriBuilder
//                .path("/ned/data/admSiList")
//                .queryParam("admCode", sido) //.substring(0, sido.length() - 1)
//                .queryParam("format", "json")
//                .queryParam("numOfRows", 100)
//                .queryParam("key", vworld)  //String keyVWorld
//                .queryParam("domain", "localhost")
//                .build())
//            .retrieve()
//            .bodyToMono(Map.class) // 응답을 Map 형태로 받음
//            .block(); // 동기 방식으로 기다림
//    }
//
//
//
//    @GetMapping("/gugun")
//    @ResponseBody
//    public Map<String, Object> getAdmGugunList(@RequestParam String gugun) { //String keyVWorld
//        return WebClient.builder().baseUrl("https://api.vworld.kr").build()
//            .get()
//            .uri(uriBuilder -> uriBuilder
//                .path("/ned/data/admDongList")
//                .queryParam("admCode", gugun)
//                .queryParam("format", "json")
//                .queryParam("numOfRows", 100)
//                .queryParam("key", vworld)  //String keyVWorld
//                .queryParam("domain", "localhost")
//                .build())
//            .retrieve()
//            .bodyToMono(Map.class) // 응답을 Map 형태로 받음
//            .block(); // 동기 방식으로 기다림
//    }
}
