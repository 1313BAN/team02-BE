package com.ssafy.team02_BE.like.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@Getter
public class UserLikeId implements Serializable {
    private Long userId;
    private String aptSeq;
}
