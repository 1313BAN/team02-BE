package com.ssafy.team02_BE.like.repository;

import com.ssafy.team02_BE.apart.domain.ApartInfo;
import com.ssafy.team02_BE.like.domain.UserLike;
import com.ssafy.team02_BE.like.domain.UserLikeId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserLikeRepository extends JpaRepository<UserLike, UserLikeId> {

    @Query("SELECT COUNT(ul) > 0 FROM UserLike ul WHERE ul.aptSeq = :aptSeq AND ul.userId = :userId")
    boolean existsByAptSeqAndUserId(@Param("aptSeq") String aptSeq, @Param("userId") Long userId);
    @Query("select u.apartInfo from UserLike u where u.userId = :userId")
    List<ApartInfo> findAllApartInfoByUserId(@Param("userId") Long userId);
}
