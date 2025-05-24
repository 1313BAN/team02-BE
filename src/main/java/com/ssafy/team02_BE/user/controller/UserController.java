package com.ssafy.team02_BE.user.controller;

import com.ssafy.team02_BE.auth.resolver.UserId;
import com.ssafy.team02_BE.common.dto.ApiResponse;
import com.ssafy.team02_BE.exception.SuccessCode;
import com.ssafy.team02_BE.user.controller.dto.UserDetailResponseDTO;
import com.ssafy.team02_BE.user.controller.dto.UserUpdateRequestDTO;
import com.ssafy.team02_BE.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "UserController", description = "유저 관련 기능")
public class UserController {
    private final UserService userService;

    @PutMapping("/update")
    @Operation(summary = "유저 정보 수정")
    public ResponseEntity<ApiResponse<UserDetailResponseDTO>> updateUser(@Parameter(hidden = true) @UserId Long userId, @RequestBody UserUpdateRequestDTO userUpdateRequestDTO) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(
                SuccessCode.UPDATE_USER_SUCCESS,
                userService.updateUser(userId, userUpdateRequestDTO)
            ));
    }

    @GetMapping("")
    @Operation(summary = "유저 정보 조회")
    public ResponseEntity<ApiResponse<UserDetailResponseDTO>> getUserDetail(@Parameter(hidden = true) @UserId Long userId) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(
                SuccessCode.GET_USER_SUCCESS,
                userService.getUserDetail(userId)
            ));
    }

}
