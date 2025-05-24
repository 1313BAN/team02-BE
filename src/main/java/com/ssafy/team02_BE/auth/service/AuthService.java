package com.ssafy.team02_BE.auth.service;

import com.ssafy.team02_BE.auth.controller.dto.LoginRequestDTO;
import com.ssafy.team02_BE.auth.controller.dto.LoginResponseDTO;
import com.ssafy.team02_BE.auth.controller.dto.SignupRequestDTO;
import com.ssafy.team02_BE.auth.controller.dto.SignupResponseDTO;
import com.ssafy.team02_BE.auth.domain.Refresh;
import com.ssafy.team02_BE.auth.domain.Role;
import com.ssafy.team02_BE.user.domain.User;
import com.ssafy.team02_BE.auth.repository.RefreshRepository;
import com.ssafy.team02_BE.user.repository.UserRepository;
import com.ssafy.team02_BE.exception.ErrorCode;
import com.ssafy.team02_BE.exception.model.ConflictException;
import com.ssafy.team02_BE.exception.model.NotFoundException;
import com.ssafy.team02_BE.security.jwt.Jwt;
import com.ssafy.team02_BE.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final RefreshRepository refreshRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * 로그인
     */
    @Transactional
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        // DTO로부터 email, password를 꺼내 token 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginRequestDTO.toAuthentication();

        // 실제 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        // ID 존재 여부 + 해당 ID로 불러온 비밀번호가 사용자가 제출한 비밀번호와 일치하는지 겅즘
        //   authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);

        return LoginResponseDTO.of((User) authentication.getPrincipal());
    }

    /**
     * 로그아웃
     */
    @Transactional
    public void logout(Long userId) {
        // refreshToken 삭제
        refreshRepository.deleteByUserId(userId);
    }

    /**
     * 회원가입
     */
    @Transactional
    public SignupResponseDTO signup(SignupRequestDTO signupRequestDTO) {
        //TODO: Util로 빼기
        //        UserServiceUtil.checkAlreadySignupSocialId(userRepository, request.socialId());
        if (userRepository.existsByEmail(signupRequestDTO.getEmail())) {
            throw new ConflictException(ErrorCode.ALREADY_EXISTS_USER);
        }

        // User 생성
        User signupUser = User.builder()
                .nickname(signupRequestDTO.getNickname())
                .email(signupRequestDTO.getEmail())
                .password(passwordEncoder.encode(signupRequestDTO.getPassword()))
                .roles(Role.USER.name())
                .build();

        return SignupResponseDTO.of(userRepository.save(signupUser));
    }

    /**
     * 액세스 토큰 재발급
     */
    @Transactional
    public Jwt reissue(String refreshToken) {
        // 토큰 검증
        jwtProvider.validateToken(refreshToken);

        // DB에 refreshToken 저장되어 있는지 확인
        if (!refreshRepository.existsByRefresh(refreshToken)) {
            throw new NotFoundException(ErrorCode.NULL_TOKEN);
        }

        // refreshToken으로부터 userId 꺼내기
        Authentication authentication = jwtProvider.getAuthenticationFromToken(refreshToken);
        Long userId = Long.parseLong((String) authentication.getPrincipal());

        // user 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.UNREGISTERED_USER));

        //Refresh 토큰으로 새로운 Jwt token을 발급
        //DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        return generateAuthTokens(user.getId());
    }

    /**
     * 회원 탈퇴
     */
    @Transactional
    public void withdraw(Long userId) {
        // User 존재 여부 확인
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.UNREGISTERED_USER));
        // User 삭제
        userRepository.deleteById(userId);
        // refreshToken 삭제
        refreshRepository.deleteByUserId(userId);
    }

    /**
     * user 정보를 통해 토큰 생성 및 쿠키 설정을 위한 공통 메서드
     */
    @Transactional
    public Jwt generateAuthTokens(Long userId) {
        // userId로 Jwt 토큰 생성
        Jwt jwt = jwtProvider.getJwtTokensFromUserId(userId);

        // DB에 refreshToken 저장
        saveRefreshToken(userId, jwt.refreshToken());

        return jwt;
    }

    /**
     * 리프레시 토큰을 DB에 저장
     */
    @Transactional
    public void saveRefreshToken(Long userId, String refreshToken) {
        // 밀리초(refreshExpiration)를 LocalDateTime으로 변환
        LocalDateTime expiryDate = LocalDateTime.ofInstant(
                Instant.now().plusMillis(jwtProvider.getRefreshTokenExpiration()),
                ZoneId.systemDefault()
        );

        // 기존 refreshToken이 있는지 확인하고, 있으면 업데이트, 없으면 새로 생성
        Refresh refresh = refreshRepository.findByUserId(userId)
                .orElse(Refresh.builder().build());

        // refreshToken 정보 설정
        refresh.setUserId(userId);
        refresh.setRefresh(refreshToken);
        refresh.setExpiration(expiryDate);

        // 저장
        refreshRepository.save(refresh);
    }
}
