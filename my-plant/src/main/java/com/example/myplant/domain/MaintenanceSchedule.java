package com.example.myplant.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceSchedule {
    private LocalDate repottingDate; // 분갈이 날짜
    private LocalDate fertilizingDate; // 영양 관리 날짜
    private LocalDate pruningDate; // 가지치기 날짜
}
