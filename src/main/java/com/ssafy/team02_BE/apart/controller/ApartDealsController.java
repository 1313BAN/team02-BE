package com.ssafy.team02_BE.apart.controller;


import com.ssafy.team02_BE.apart.domain.ApartDeals;
import com.ssafy.team02_BE.apart.service.ApartDealsService;
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
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/map/apart")
@Tag(name = "ApartDealsController", description = "아파트 거래 관련 기능")
public class ApartDealsController {
	private final ApartDealsService apartDealsService;

	@GetMapping("/{aptSeq}/deals")
	@Operation(summary = "aptSeq로 아파트 거래 정보 조회")
	public ResponseEntity<ApiResponse<List<ApartDeals>>> getApartDealsByAptSeq(@PathVariable String aptSeq) throws Exception {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiResponse.success(
				SuccessCode.GET_APARTDEALS_BY_APTSEQ,
				apartDealsService.getApartDealsByAptSeq(aptSeq)
			));
	}

}
