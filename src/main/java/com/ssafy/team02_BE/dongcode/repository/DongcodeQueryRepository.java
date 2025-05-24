package com.ssafy.team02_BE.dongcode.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.team02_BE.dongcode.domain.QDongcode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DongcodeQueryRepository {

    private final JPAQueryFactory queryFactory;

    /**
           * 시도 코드 조회
     */
    public List<String> findDistinctSidoList() {
        QDongcode dongcode = QDongcode.dongcode;

        return queryFactory
            .selectDistinct(dongcode.sidoName)
            .from(dongcode)
            .fetch();
    }

    /**
          * 시에 대한 구군 코드 조회
          */
    public List<String> findDistinctGugunList(String sidoName) {
        QDongcode dongcode = QDongcode.dongcode;

        return queryFactory
            .selectDistinct(dongcode.gugunName)
            .from(dongcode)
            .where(dongcode.sidoName.eq(sidoName))
            .fetch();
    }

        /**
     * 구군에 대한 동 코드 조회
     */
    public List<String> findDistinctDongList(String sidoName, String gugunName) {
        QDongcode dongcode = QDongcode.dongcode;

        return queryFactory
            .selectDistinct(dongcode.dongName)
            .from(dongcode)
            .where(dongcode.sidoName.eq(sidoName),
                dongcode.gugunName.eq(gugunName))
            .fetch();
    }
}

//    /**
//     * 시도 코드 조회
//     */
//    public List<AdmVO> findDistinctSidoList() {
//        QDongcode dongcode = QDongcode.dongcode;
//
//        return queryFactory
//            .select(Projections.constructor(AdmVO.class,
//                dongcode.dongCode.substring(0, 2).as("admCode"),
//                dongcode.sidoName.as("admCodeNm"),
//                dongcode.sidoName.as("lowestAdmCodeNm")
//            ))
//            .from(dongcode)
//            .groupBy(dongcode.dongCode.substring(0, 2), dongcode.sidoName)
//            .fetch();
//    }
//
//    public String findSidoNameBySidoCode(String sidoCode) {
//        QDongcode dongcode = QDongcode.dongcode;
//
//        return queryFactory
//            .selectDistinct(
//                dongcode.sidoName
//            )
//            .from(dongcode)
//            .where(
//                dongcode.dongCode.substring(0, 2).eq(sidoCode)
//            )
//            .fetchOne();
//    }
//
//    /**
//     * 시에 대한 구군 코드 조회
//     */
//    public List<AdmVO> findDistinctGugunList(String sidoName) {
//        QDongcode dongcode = QDongcode.dongcode;
//
//        return queryFactory
//            .selectDistinct(Projections.constructor(AdmVO.class,
//                dongcode.dongCode.substring(0, 5).as("admCode"),  // 시 + 구 코드
//                dongcode.sidoName.concat(" ").concat(dongcode.gugunName).as("admCodeNm"),
//                dongcode.gugunName.as("lowestAdmCodeNm")
//            ))
//            .from(dongcode)
//            .where(
//                dongcode.sidoName.eq(sidoName)
//            )
//            .groupBy( dongcode.dongCode.substring(0, 5),dongcode.gugunName, dongcode.sidoName)
//            .orderBy(dongcode.gugunName.asc())
//            .fetch();
//
//    }
//
//    /**
//     * 구군에 대한 동 코드 조회
//     */
//    public List<AdmVO> findDistinctDongList(String sidoGugunCode) {
//        QDongcode dongcode = QDongcode.dongcode;
//
//        return queryFactory
//            .select(Projections.constructor(AdmVO.class,
//                dongcode.dongCode.substring(0, 8).as("admCode"),
//                dongcode.sidoName.concat(" ").concat(dongcode.gugunName).concat(" ").concat(dongcode.dongName).as("admCodeNm"),
//                dongcode.dongName.as("lowestAdmCodeNm")
//            ))
//            .from(dongcode)
//            .where(dongcode.dongCode.substring(0, 5).eq(sidoGugunCode).and(dongcode.dongName.isNotNull()))
//            .fetch();
//    }

