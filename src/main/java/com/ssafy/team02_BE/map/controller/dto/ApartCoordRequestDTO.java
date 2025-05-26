package com.ssafy.team02_BE.map.controller.dto;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class ApartCoordRequestDTO {
    private String aptSeq;
    private Double x;
    private Double y;
}

