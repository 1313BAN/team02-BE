package com.ssafy.team02_BE.apart.repository;

import com.ssafy.team02_BE.apart.domain.ApartDeals;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartDealsRepository extends JpaRepository<ApartDeals, Long> {

    List<ApartDeals> findByAptSeq(String aptSeq);
}
