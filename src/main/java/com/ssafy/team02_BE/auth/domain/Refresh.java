package com.ssafy.team02_BE.auth.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Refresh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String refresh;
    private LocalDateTime expiration;

    @Builder
    public Refresh(Long userId, String refresh, LocalDateTime expiration) {
        this.userId = userId;
        this.refresh = refresh;
        this.expiration = expiration;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }
}