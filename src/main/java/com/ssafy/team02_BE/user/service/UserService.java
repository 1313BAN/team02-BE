package com.ssafy.team02_BE.user.service;

import com.ssafy.team02_BE.apart.domain.ApartInfo;
import com.ssafy.team02_BE.exception.ErrorCode;
import com.ssafy.team02_BE.exception.model.NotFoundException;
import com.ssafy.team02_BE.like.repository.UserLikeRepository;
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
//    private final PasswordEncoder passwordEncoder;

    /**
     * 유저 정보 수정
     */
    @Transactional
    public UserUpdateResponseDTO updateUser(Long userId, UserUpdateRequestDTO userUpdateRequestDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.UNREGISTERED_USER));

        user.setNickname(userUpdateRequestDTO.getNickname());
        user.setPassword(!userUpdateRequestDTO.isNullPassword() ? userUpdateRequestDTO.getPassword() : user.getPassword());
        user.setName(userUpdateRequestDTO.getName());
        user.setPhoneNumber(userUpdateRequestDTO.getPhoneNumber());

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
    public boolean existsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(ErrorCode.UNREGISTERED_USER);
        }
        return true;
    }
}