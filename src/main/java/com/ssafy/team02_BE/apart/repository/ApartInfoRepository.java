package com.ssafy.team02_BE.apart.repository;

import com.ssafy.team02_BE.apart.domain.ApartInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartInfoRepository extends JpaRepository<ApartInfo, String> {

    Optional<ApartInfo> findByAptSeq(String aptSeq);
    boolean existsByAptSeq(String aptSeq);
}
