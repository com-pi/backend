package com.example.myplant.domain;

import lombok.*;


import java.time.LocalDate;

import jakarta.persistence.Embeddable;


@Embeddable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MaintenanceSchedule {

    private LocalDate repottingDate;
    private LocalDate fertilizingDate;
    private LocalDate pruningDate;
}