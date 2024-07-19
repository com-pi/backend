package com.example.myplant.adapter.in.web.response;

import com.example.myplant.domain.Schedule;

import java.time.LocalDate;

public record ScheduleCalendarResponse(
        LocalDate startDate,
        Long scheduleId,
        String colorType
) {
    public static ScheduleCalendarResponse from(Schedule schedule) {
        return new ScheduleCalendarResponse(
                schedule.getStartDateTime().toLocalDate(),
                schedule.getScheduleId(),
                schedule.getColorType()
        );
    }
}
