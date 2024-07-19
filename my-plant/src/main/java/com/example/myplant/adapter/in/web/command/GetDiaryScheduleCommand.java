package com.example.myplant.adapter.in.web.command;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class GetDiaryScheduleCommand {
    private LocalDate startDate;
    private LocalDate endDate;
    private Long memberId;

    public static GetDiaryScheduleCommand of(LocalDate startDate, LocalDate endDate, Long memberId) {
        return GetDiaryScheduleCommand.builder()
                .startDate(startDate)
                .endDate(endDate)
                .memberId(memberId)
                .build();
    }
}
