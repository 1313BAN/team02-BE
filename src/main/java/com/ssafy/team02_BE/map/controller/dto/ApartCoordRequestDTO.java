package com.ssafy.team02_BE.map.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApartCoordRequestDTO {
    private List<ApartCoord> apartCoords;

    @Getter
    @Builder
    public static class ApartCoord {
        private String address;
        private String aptSeq;
        private String label;
        private Utmk utmk;
    }

    @Getter
    @Builder
    public static class Utmk {
        private InnerData data;
    }

    @Getter
    @Builder
    public static class InnerData {
        private String addrType;
        private String admCd;
        private String admNm;
        private String bdMainNm;
        private String bdSubNm;
        private String jibunMainNo;
        private String jibunSubNo;
        private String legCd;
        private String legNm;
        private String riCd;
        private String riNm;
        private String roadCd;
        private String roadNm;
        private String roadNmMainNo;
        private String roadNmSubNo;
        private String sggCd;
        private String sggNm;
        private String sidoCd;
        private String sidoNm;
        private String x;
        private String y;
    }

}

