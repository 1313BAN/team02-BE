package com.ssafy.team02_BE.apart.repository;

import com.ssafy.team02_BE.apart.domain.ApartInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartInfoRepository extends JpaRepository<ApartInfo, String> {

    ApartInfo findByAptSeq(String aptSeq);
    Boolean existsByAptSeq(String aptSeq);
}
