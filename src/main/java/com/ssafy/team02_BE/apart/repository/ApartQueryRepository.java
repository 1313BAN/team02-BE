package com.ssafy.team02_BE.apart.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.team02_BE.apart.domain.ApartInfo;
import com.ssafy.team02_BE.apart.domain.QApartInfo;
import com.ssafy.team02_BE.dongcode.domain.QDongcode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ApartQueryRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 동이름으로 아파트 상세 정보 리스트 조회
     */
    public List<ApartInfo> getHouseInfosByDongName(String dongName) {
        QApartInfo apartInfo = QApartInfo.apartInfo;
        QDongcode dongcode = QDongcode.dongcode;
        return queryFactory
            .selectFrom(apartInfo)
            .join(dongcode).on(apartInfo.sggCd.concat(apartInfo.umdCd).eq(dongcode.dongCode))
            .where(dongcode.dongName.eq(dongName))
            .fetch();
    }
}
