package com.ssafy.team02_BE.like.domain;

import com.ssafy.team02_BE.apart.domain.ApartInfo;
import com.ssafy.team02_BE.common.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(UserLikeId.class)
public class UserLike extends BaseEntity {
    @Id
    private Long userId;

    @Id
    private String aptSeq;

    @ManyToOne
    @JoinColumn(name = "aptSeq", insertable = false, updatable = false)
    private ApartInfo apartInfo;

    @Builder
    public UserLike(Long userId, String aptSeq) {
        this.userId = userId;
        this.aptSeq = aptSeq;
    }

}
