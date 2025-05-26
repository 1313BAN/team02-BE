package com.ssafy.team02_BE.map.service;

import com.ssafy.team02_BE.map.controller.dto.ApartCoordRequestDTO;
import com.ssafy.team02_BE.map.domain.ApartCoord;
import com.ssafy.team02_BE.map.repository.ApartCoordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApartCoordService {
    private final ApartCoordRepository apartCoordRepository;

    @Transactional
    public void insertApartCoords(ApartCoordRequestDTO apartAddressRequestDTO) {
        apartAddressRequestDTO.getApartCoords().forEach(apartCoordDTO -> {
            ApartCoord apartCoord = ApartCoord.builder().aptSeq(apartCoordDTO.getAptSeq())
                    .x(apartCoordDTO.getUtmk().getData().getX())
                    .y(apartCoordDTO.getUtmk().getData().getY())
                    .build();
            apartCoordRepository.save(apartCoord);
        });
    }
}
