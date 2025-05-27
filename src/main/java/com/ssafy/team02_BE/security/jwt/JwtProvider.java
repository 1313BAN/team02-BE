package com.ssafy.team02_BE.security.jwt;

import com.ssafy.team02_BE.auth.domain.Role;
import com.ssafy.team02_BE.exception.ErrorCode;
import com.ssafy.team02_BE.exception.model.UnauthorizedException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth"; //role에 대한 정보가 담김
    private static final String TOKEN_TYPE_KEY = "type";
    private static final String ACCESS_TOKEN_TYPE = "access";
    private static final String REFRESH_TOKEN_TYPE = "refresh";
    // 비밀번호 재설정을 위한 상수
    private static final String PASSWORD_RESET_TOKEN_TYPE = "password_reset"; // 비밀번호 재설정 토큰 타입
    private static final Long PASSWORD_RESET_EXPIRATION = 600L; // 10분 = 600초

    private final String secretCode;
    private final Long accessTokenExpiration;
    private final Long refreshTokenExpiration;

    public Long getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }

    private SecretKey key;

    public JwtProvider(
            @Value("${jwt.secret}") String secretCode,
            @Value("${jwt.access-expiration}") Long accessTokenExpiration,
            @Value("${jwt.refresh-expiration}") Long refreshTokenExpiration
    ) {
        this.secretCode = secretCode;
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    @Override
    public void afterPropertiesSet() {
        key = new SecretKeySpec(secretCode.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    /**
     * @param principal - userId
     * @return Jwt(accessToken, refreshToken)
     */
    public Jwt getJwtTokensFromUserId(Long principal) {
        //pricipal(userId)로 authenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principal, null, List.of(new SimpleGrantedAuthority(Role.USER.name())));
        //accessToken, refreshToken 생성
        return createJwtToken(authenticationToken);
    }

    /**
     * @param authentication - userId, roles 등이 담김
     * @return Jwt(accessToken, refreshToken)
     */
    private Jwt createJwtToken(Authentication authentication) {
        // roles 꺼내기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        // JwtToken 생성
        String accessToken = generateToken(authentication.getName(), authorities, ACCESS_TOKEN_TYPE, accessTokenExpiration);
        String refreshToken = generateToken(authentication.getName(), authorities, REFRESH_TOKEN_TYPE, refreshTokenExpiration);

        return Jwt.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * 토큰 정보를 통해 jwtToken 생성
     *
     * @param subject     - userId
     * @param authorities - role
     * @param tokenType   - access / refresh
     * @param expiration  - 토큰 만료시간
     * @return - jwtToken
     */
    private String generateToken(String subject, String authorities, String tokenType, Long expiration) {

        long now = (new Date()).getTime();
        Date expirationDate = new Date(now + expiration * 1000); // 현재 시간 + 만료 기간까지만 유효

        return Jwts.builder().subject(subject) // subject = id 정보
                .claim(AUTHORITIES_KEY, authorities) //auth = role 정보
                .claim(TOKEN_TYPE_KEY, tokenType) //type = access or refresh
                .issuedAt(new Date(System.currentTimeMillis())) //현재 발행 시간 -> 들어가는 값이 달라야 생성되는 토큰도 다름
                .signWith(key, SignatureAlgorithm.HS256)
                .expiration(expirationDate)
                .compact();
    }

    /**
     * @param token - access or refresh
     * @return Authentication - userId를 바탕으로 만든 객체
     */
    public Authentication getAuthenticationFromToken(String token) {
        // token 파싱
        Claims claims = parseClaims(token);

        if (claims.get("auth") == null || claims.get("type") == null || claims.getSubject() == null) {
            throw new UnauthorizedException(ErrorCode.MISSING_CLAIM_TOKEN);
        }

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        // token에서 꺼낸 userId(subject)를 바탕으로 AuthenticationToken을 생성
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), "", authorities);
    }

    /**
     * 토큰 검증
     *
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(ErrorCode.EXPIRED_TOKEN);
        } catch (MalformedJwtException e) {
            throw new UnauthorizedException(ErrorCode.MALFORMED_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedException(ErrorCode.NULL_TOKEN);
        } catch (SignatureException e) {
            throw new UnauthorizedException(ErrorCode.MALFORMED_TOKEN);
        } catch (Exception e) {
            throw new UnauthorizedException(ErrorCode.UNAUTHORIZED_TOKEN);
        }
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build().parseSignedClaims(accessToken).getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    /**
     * Authentication 객체에서 userId(Long) 추출
     *
     * @param authentication Authentication 객체 (userId가 principal에 담김)
     * @return userId
     */
    public Long extractUserIdFromAuthentication(Authentication authentication) {
        try {
            return Long.parseLong(authentication.getPrincipal().toString());
        } catch (NumberFormatException e) {
            throw new UnauthorizedException(ErrorCode.INVALID_TOKEN_PAYLOAD);
        }
    }

    /**
     * 비밀번호 재설정을 위한 10분 유효기간 토큰 생성
     * @param userId 사용자 ID
     * @return 비밀번호 재설정 토큰
     */
    public String generatePasswordResetToken(Long userId) {
        return generateToken(
                userId.toString(),                   // subject: 사용자 ID를 문자열로 변환
                "PASSWORD_RESET",                    // authorities: 비밀번호 재설정 권한
                PASSWORD_RESET_TOKEN_TYPE,           // tokenType: password_reset
                PASSWORD_RESET_EXPIRATION            // expiration: 600초 (10분)
        );
    }

    /**
     * 비밀번호 재설정 토큰 검증
     * @param token 검증할 토큰
     * @return 토큰이 유효한 비밀번호 재설정 토큰인지 여부
     */
    public boolean isValidPasswordResetToken(String token) {
        try {
            Claims claims = parseClaims(token);
            String tokenType = claims.get(TOKEN_TYPE_KEY, String.class);
            return PASSWORD_RESET_TOKEN_TYPE.equals(tokenType) &&
                    !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 비밀번호 재설정 토큰에서 사용자 ID 추출
     * @param token 비밀번호 재설정 토큰
     * @return 사용자 ID
     */
    public Long getUserIdFromPasswordResetToken(String token) {
        try {
            if (!isValidPasswordResetToken(token)) {
                throw new UnauthorizedException(ErrorCode.INVALID_TOKEN_PAYLOAD);
            }
            Claims claims = parseClaims(token);
            return Long.parseLong(claims.getSubject());
        } catch (NumberFormatException e) {
            throw new UnauthorizedException(ErrorCode.INVALID_TOKEN_PAYLOAD);
        }
    }
}