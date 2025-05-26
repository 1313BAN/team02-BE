package com.ssafy.team02_BE.apart.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.team02_BE.apart.controller.dto.ApartDongNameResponseDTO;
import com.ssafy.team02_BE.apart.domain.ApartInfo;
import com.ssafy.team02_BE.apart.domain.QApartInfo;
import com.ssafy.team02_BE.dongcode.domain.QDongcode;
import com.ssafy.team02_BE.map.domain.ApartCoord;
import com.ssafy.team02_BE.map.domain.QApartCoord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ApartQueryRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 동이름으로 아파트 상세 정보 리스트 조회
     */
    public List<ApartDongNameResponseDTO> getApartInfosByDongName(String sidoName, String gugunName, String dongName) {
        QApartInfo apartInfo = QApartInfo.apartInfo;
        QDongcode dongcode = QDongcode.dongcode;
        QApartCoord apartCoord = QApartCoord.apartCoord;

        return queryFactory
                .select(apartInfo, apartCoord)
                .from(apartInfo)
                .join(dongcode).on(apartInfo.sggCd.concat(apartInfo.umdCd).eq(dongcode.dongCode))
                .leftJoin(apartCoord).on(apartInfo.aptSeq.eq(apartCoord.aptSeq))
                .where(
                        dongcode.sidoName.eq(sidoName),
                        dongcode.gugunName.eq(gugunName),
                        dongcode.dongName.eq(dongName)
                )
                .fetch()
                .stream()
                .map(result -> {
                    ApartInfo info = result.get(apartInfo);
                    ApartCoord coord = result.get(apartCoord);
                    return ApartDongNameResponseDTO.of(
                            info.getAptSeq(),
                            info.getSggCd(),
                            info.getUmdCd(),
                            info.getUmdNm(),
                            info.getJibun(),
                            info.getRoadNmSggCd(),
                            info.getRoadNm(),
                            info.getRoadNmBonbun(),
                            info.getRoadNmBubun(),
                            info.getAptNm(),
                            info.getBuildYear(),
                            info.getLatitude(),
                            info.getLongitude(),
                            coord != null ? (coord.getX() != null ? coord.getX() : 0.0) : 0.0,
                            coord != null ? (coord.getY() != null ? coord.getY() : 0.0) : 0.0
                    );
                })
                .collect(Collectors.toList());
    }

}
