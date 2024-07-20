package com.example.myplant.adapter.in.web.command;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class GetScheduleCalendarCommand {
    private LocalDate date;
    private Long memberId;

    public static GetScheduleCalendarCommand of(LocalDate date, Long memberId) {
        return GetScheduleCalendarCommand.builder()
                .date(date)
                .memberId(memberId)
                .build();
    }
}
