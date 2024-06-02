package com.example.myplant.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDate;


@Embeddable
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MaintenanceSchedule {

    private LocalDate repottingDate;
    private LocalDate fertilizingDate;
    private LocalDate pruningDate;
    private int intervalInMonths; // 3개월, 6개월, 9개월, 12개월

    @Builder
    public MaintenanceSchedule(LocalDate repottingDate, LocalDate fertilizingDate, LocalDate pruningDate, int intervalInMonths){
        this.repottingDate = repottingDate;
        this.fertilizingDate = fertilizingDate;
        this.pruningDate = pruningDate;
        this.intervalInMonths = intervalInMonths;
    }
}