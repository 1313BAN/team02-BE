package com.ssafy.team02_BE.user.service;

import com.ssafy.team02_BE.apart.domain.ApartInfo;
import com.ssafy.team02_BE.exception.ErrorCode;
import com.ssafy.team02_BE.exception.model.NotFoundException;
import com.ssafy.team02_BE.like.repository.UserLikeRepository;
import com.ssafy.team02_BE.security.jwt.JwtProvider;
import com.ssafy.team02_BE.user.controller.dto.ResetPasswordRequestDTO;
import com.ssafy.team02_BE.user.controller.dto.UserDetailResponseDTO;
import com.ssafy.team02_BE.user.controller.dto.UserUpdateRequestDTO;
import com.ssafy.team02_BE.user.controller.dto.UserUpdateResponseDTO;
import com.ssafy.team02_BE.user.domain.User;
import com.ssafy.team02_BE.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserLikeRepository userLikeRepository;
    private final JwtProvider jwtProvider;
//    private final PasswordEncoder passwordEncoder;

    /**
     * 유저 정보 수정
     */
    @Transactional
    public UserUpdateResponseDTO updateUser(Long userId, UserUpdateRequestDTO userUpdateRequestDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.UNREGISTERED_USER));

        user.setNickname(userUpdateRequestDTO.getNickname());
        user.setName(userUpdateRequestDTO.getName());
        user.setPhoneNumber(userUpdateRequestDTO.getPhoneNumber());
        user.setPassword(userUpdateRequestDTO.isNullPassword() ? user.getPassword() : userUpdateRequestDTO.getPassword());
        return UserUpdateResponseDTO.of(user);
    }

    /**
     * 유저 정보 조회
     */
    @Transactional(readOnly = true)
    public UserDetailResponseDTO getUserDetail(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.UNREGISTERED_USER));
        List<ApartInfo> userLikes = userLikeRepository.findAllApartInfoByUserId(user.getId());
        return UserDetailResponseDTO.of(user, userLikes);
    }

    /**
     * 유저 등록 여부 확인
     */
    @Transactional(readOnly = true)
    public void existsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(ErrorCode.UNREGISTERED_USER);
        }
    }

    @Transactional
    public void resetPassword(ResetPasswordRequestDTO resetPasswordRequestDTO) {
        Long userId = jwtProvider.getUserIdFromPasswordResetToken(resetPasswordRequestDTO.getPasswordResetToken());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.UNREGISTERED_USER));

        // 비밀번호가 null이 아니면, password를 변경
        if (!resetPasswordRequestDTO.isNullPassword() && resetPasswordRequestDTO.getPassword() != null) {
            user.setPassword(resetPasswordRequestDTO.getPassword()); // 비밀번호를 그대로 저장
        }
    }
}