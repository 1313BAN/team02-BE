package com.ssafy.team02_BE.apart.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApartDongNameResponseDTO {
    private String aptSeq;
    private String sggCd;
    private String umdCd;
    private String umdNm;
    private String jibun;
    private String roadNmSggCd;
    private String roadNm;
    private String roadNmBonbun;
    private String roadNmBubun;
    private String aptNm;
    private int buildYear;
    private String latitude;
    private String longitude;
    private Double x;
    private Double y;

    public static ApartDongNameResponseDTO of(String aptSeq, String sggCd, String umdCd, String umdNm, String jibun, String roadNmSggCd, String roadNm, String roadNmBonbun, String roadNmBubun, String aptNm, int buildYear, String latitude, String longitude, Double x, Double y) {
        return ApartDongNameResponseDTO.builder()
                .aptSeq(aptSeq)
                .sggCd(sggCd)
                .umdCd(umdCd)
                .umdNm(umdNm)
                .jibun(jibun)
                .roadNmSggCd(roadNmSggCd)
                .roadNm(roadNm)
                .roadNmBonbun(roadNmBonbun)
                .roadNmBubun(roadNmBubun)
                .aptNm(aptNm)
                .buildYear(buildYear)
                .latitude(latitude)
                .longitude(longitude)
                .x(x)
                .y(y)
                .build();
    }

}
