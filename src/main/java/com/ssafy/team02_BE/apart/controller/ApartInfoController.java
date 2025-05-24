package com.ssafy.team02_BE.apart.controller;


import com.ssafy.team02_BE.apart.domain.ApartInfo;
import com.ssafy.team02_BE.apart.service.ApartInfoService;
import com.ssafy.team02_BE.common.dto.ApiResponse;
import com.ssafy.team02_BE.exception.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/map/apart")
@Tag(name = "ApartInfoController", description = "아파트 상세정보 관련 기능")
public class ApartInfoController {
	private final ApartInfoService apartInfoService;

	@PostMapping("/{aptSeq}")
	@Operation(summary = "aptSeq로 아파트 상세 정보 조회")
	public ResponseEntity<ApiResponse<ApartInfo>> getApartInfoByAptSeq(@PathVariable String aptSeq) throws Exception {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.success(
				SuccessCode.GET_APARTINFO_BY_APTSEQ,
				apartInfoService.getApartInfoByAptSeq(aptSeq)
			));
	}
	


}
