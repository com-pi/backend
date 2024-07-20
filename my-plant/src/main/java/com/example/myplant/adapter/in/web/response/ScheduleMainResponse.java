package com.example.myplant.adapter.in.web.response;

import com.example.myplant.domain.Schedule;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ScheduleMainResponse(
    Long scheduleId,
    String title,
    LocalDateTime startDateTime,
    Boolean isCompleted,
    String endDateTimeMessage
) {

    public static ScheduleMainResponse from(LocalDateTime now, Schedule schedule) {
        return ScheduleMainResponse.builder()
            .scheduleId(schedule.getScheduleId())
            .title(schedule.getTitle())
            .startDateTime(schedule.getStartDateTime())
            .isCompleted(schedule.getIsCompleted())
            .endDateTimeMessage(schedule.getDdayMessage(now))
            .build();
    }

}
