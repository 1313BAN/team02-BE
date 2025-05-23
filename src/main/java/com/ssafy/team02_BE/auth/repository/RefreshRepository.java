package com.ssafy.team02_BE.auth.repository;

import com.ssafy.team02_BE.auth.domain.Refresh;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshRepository extends JpaRepository<Refresh, Long> {
    Boolean existsByRefresh(String refresh);

    void deleteByUserId(Long userId);

    Optional<Refresh> findByUserId(Long userId);
}
