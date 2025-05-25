package com.ssafy.team02_BE.like.controller;

import com.ssafy.team02_BE.apart.domain.ApartInfo;
import com.ssafy.team02_BE.auth.resolver.UserId;
import com.ssafy.team02_BE.common.dto.ApiResponse;
import com.ssafy.team02_BE.exception.SuccessCode;
import com.ssafy.team02_BE.like.domain.UserLike;
import com.ssafy.team02_BE.like.service.UserLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
@Tag(name = "UserLikeController", description = "유저 찜 관련 기능")
public class UserLikeController {

    private final UserLikeService userLikeService;

    @PostMapping("/{aptSeq}")
    @Operation(summary = "매물 찜하기")
    public ResponseEntity<ApiResponse<UserLike>> like(@PathVariable String aptSeq, @Parameter(hidden = true) @UserId Long userId) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(
                SuccessCode.POST_LIKE_SUCCESS,
                userLikeService.like(aptSeq, userId)
            ));
    }

    @DeleteMapping("/{aptSeq}")
    @Operation(summary = "매물 찜 취소")
    public ResponseEntity<ApiResponse<Void>> unlike(@PathVariable String aptSeq, @Parameter(hidden = true) @UserId Long userId) {
        userLikeService.unlike(aptSeq, userId);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(
                SuccessCode.DELETE_LIKE_SUCCESS
            ));
    }

    @GetMapping("")
    @Operation(summary = "내가 찜한 매물 조회")
    public ResponseEntity<ApiResponse<List<ApartInfo>>> getUserLikeListByUserId(@Parameter(hidden = true) @UserId Long userId) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(
                SuccessCode.GET_LIKES_SUCCESS,
                userLikeService.getUserLikeListByUserId(userId)
            ));
    }
}
