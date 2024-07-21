package com.example.myplant.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class CompletedSchedule {

    private Long completedScheduleId;

    private Long scheduleId;

    private LocalDate completedDate;

}
