package com.ssafy.team02_BE.dongcode.repository;

import com.ssafy.team02_BE.dongcode.domain.Dongcode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DongcodeRepository extends JpaRepository<Dongcode, String> {
}
