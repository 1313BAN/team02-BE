package com.ssafy.team02_BE.user.service;

import com.ssafy.team02_BE.exception.ErrorCode;
import com.ssafy.team02_BE.exception.model.NotFoundException;
import com.ssafy.team02_BE.user.controller.dto.UserDetailResponseDTO;
import com.ssafy.team02_BE.user.controller.dto.UserUpdateRequestDTO;
import com.ssafy.team02_BE.user.domain.User;
import com.ssafy.team02_BE.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 유저 정보 수정
     */
    @Transactional
    public UserDetailResponseDTO updateUser(Long userId, UserUpdateRequestDTO userUpdateRequestDTO) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.UNREGISTERED_USER));

        user.setNickname(userUpdateRequestDTO.getNickname());
        user.setPassword(passwordEncoder, userUpdateRequestDTO.getPassword());
        user.setName(userUpdateRequestDTO.getName());
        user.setPhoneNumber(userUpdateRequestDTO.getPhoneNumber());

        return UserDetailResponseDTO.of(user);
    }

    /**
     * 유저 정보 조회
     */
    @Transactional(readOnly = true)
    public UserDetailResponseDTO getUserDetail(Long userId){
        return UserDetailResponseDTO.of(userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.UNREGISTERED_USER)));
    }

    /**
     * 유저 등록 여부 확인
     */
    public boolean existsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(ErrorCode.UNREGISTERED_USER);
        }
        return true;
    }
}