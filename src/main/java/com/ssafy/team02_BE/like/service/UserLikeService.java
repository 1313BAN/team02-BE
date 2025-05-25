package com.ssafy.team02_BE.like.service;

import com.ssafy.team02_BE.apart.domain.ApartInfo;
import com.ssafy.team02_BE.apart.service.ApartInfoService;
import com.ssafy.team02_BE.exception.ErrorCode;
import com.ssafy.team02_BE.exception.model.ConflictException;
import com.ssafy.team02_BE.exception.model.NotFoundException;
import com.ssafy.team02_BE.like.domain.UserLike;
import com.ssafy.team02_BE.like.domain.UserLikeId;
import com.ssafy.team02_BE.like.repository.UserLikeRepository;
import com.ssafy.team02_BE.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserLikeService {

    private final UserLikeRepository userLikeRepository;
    private final UserService userService;
    private final ApartInfoService apartInfoService;

    private void validateUserLikeId(String aptSeq, Long userId) {
        userService.existsByUserId(userId);
        apartInfoService.existsByAptSeq(aptSeq);
    }

    /**
     * 매물 찜하기
     */
    @Transactional
    public UserLike like(String aptSeq, Long userId) {
        validateUserLikeId(aptSeq, userId);

        if (userLikeRepository.existsByAptSeqAndUserId(aptSeq, userId)) {
            throw new ConflictException(ErrorCode.ALREADY_EXISTS_LIKE);
        }

        UserLike like = UserLike.builder().aptSeq(aptSeq).userId(userId).build();
        return userLikeRepository.save(like);
    }

    /**
     * 매물 찜 취소
     */
    @Transactional
    public void unlike(String aptSeq, Long userId) {
        validateUserLikeId(aptSeq, userId);
        UserLike userLike = userLikeRepository.findById(
                UserLikeId.builder().aptSeq(aptSeq).userId(userId).build())
            .orElseThrow(() -> new NotFoundException(ErrorCode.UNREGISTERED_LIKE));

        userLikeRepository.delete(userLike);
    }

    /**
     * 내가 찜한 매물 조회
     */
    @Transactional(readOnly = true)
    public List<ApartInfo> getUserLikeListByUserId(Long userId) {
        userService.existsByUserId(userId);
        return userLikeRepository.findAllApartInfoByUserId(userId);
    }
}
