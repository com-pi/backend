package com.example.myplant.domain;

import lombok.*;
import jakarta.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WateringInfo {

    private int intervalInDays; // n일마다 1회 물 주기

    @Builder
    public WateringInfo(int intervalInDays) {
        this.intervalInDays = intervalInDays;
    }

}