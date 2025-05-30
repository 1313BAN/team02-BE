package com.ssafy.team02_BE.map.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApartCoord {
    @Id
    private String aptSeq;
    private Double x;
    private Double y;

    @Builder
    public ApartCoord(String aptSeq, Double x, Double y) {
        this.aptSeq = aptSeq;
        this.x = x;
        this.y = y;
    }

}
