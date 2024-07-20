package com.example.myplant.adapter.in.web.response;

import java.time.LocalDate;

public record ScheduleCalendarResponse(
        LocalDate startDate,
        String colorType
) {
    public static ScheduleCalendarResponse from(LocalDate startDate, String colorType) {
        return new ScheduleCalendarResponse(
                startDate,
                colorType
        );
    }
}
