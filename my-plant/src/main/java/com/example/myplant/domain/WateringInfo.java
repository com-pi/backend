package com.example.myplant.domain;

import lombok.*;
import jakarta.persistence.Embeddable;

@Embeddable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WateringInfo {

    private int intervalInWeeks;
    private int frequency;
}