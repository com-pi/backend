package com.example.boardservice.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Embeddable
public class Area {

    @OneToOne(fetch = FetchType.LAZY)
    private Sido sido;

    @OneToOne(fetch = FetchType.LAZY)
    private Sigungu sigungu;

    @OneToOne(fetch = FetchType.LAZY)
    private Eupmyundong eupmyundong;

    public static Area of(Long sidoId, Long sigunguId, Long eupmyundongId) {
        return new Area(
                Sido.ofId(sidoId),
                Sigungu.ofId(sigunguId),
                Eupmyundong.ofId(eupmyundongId)
        );
    }

}
