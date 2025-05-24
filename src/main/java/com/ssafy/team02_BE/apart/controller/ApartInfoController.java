package com.ssafy.team02_BE.apart.controller;


import com.ssafy.team02_BE.apart.controller.dto.AptDetailResponseDTO;
import com.ssafy.team02_BE.apart.domain.ApartInfo;
import com.ssafy.team02_BE.apart.service.ApartInfoService;
import com.ssafy.team02_BE.common.dto.ApiResponse;
import com.ssafy.team02_BE.exception.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/map/apart")
@Tag(name = "ApartInfoController", description = "아파트 상세정보 관련 기능")
public class ApartInfoController {
	private final ApartInfoService apartInfoService;

	@GetMapping("/{aptSeq}")
	@Operation(summary = "aptSeq로 아파트 상세 정보 조회")
	public ResponseEntity<ApiResponse<ApartInfo>> getApartInfoByAptSeq(@PathVariable String aptSeq) throws Exception {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.success(
				SuccessCode.GET_APARTINFO_BY_APTSEQ,
				apartInfoService.getApartInfoByAptSeq(aptSeq)
			));
	}

	@GetMapping("/{aptSeq}/detail")
	@Operation(summary = "aptSeq로 아파트 상세 정보와 거래 정보 조회")
	public ResponseEntity<ApiResponse<AptDetailResponseDTO>> getApartDetailsByAptSeq(@PathVariable String aptSeq) throws Exception {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.success(
				SuccessCode.GET_APARTDETAILS_BY_APTSEQ,
				apartInfoService.getApartDetailsByAptSeq(aptSeq)
			));
	}

	@GetMapping("")
	@Operation(summary = "동이름으로 아파트 검색")
	public ResponseEntity<ApiResponse<List<ApartInfo>>> getApartInfosByDongName(@RequestParam String dongName) throws Exception {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.success(
				SuccessCode.GET_APARTINFOS_BY_DONGNAME,
				apartInfoService.getApartInfosByDongName(dongName)
			));
	}

}
