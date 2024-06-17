package com.example.myplant.adapter.in.web.response;

import java.time.LocalDateTime;

import com.example.myplant.domain.Schedule;
import lombok.Builder;

@Builder
public record ScheduleMainResponse(
    Long scheduleId,
    String title,
    LocalDateTime endDateTime,
    Boolean isCompleted,
    String endDateTimeMessage
) {

    public static ScheduleMainResponse from(LocalDateTime now, Schedule schedule) {
        return ScheduleMainResponse.builder()
            .scheduleId(schedule.getScheduleId())
            .title(schedule.getTitle())
            .endDateTime(schedule.getEndDateTime())
            .isCompleted(schedule.getIsCompleted())
            .endDateTimeMessage(schedule.getEndDateTimeMessage(now))
            .build();
    }

}
