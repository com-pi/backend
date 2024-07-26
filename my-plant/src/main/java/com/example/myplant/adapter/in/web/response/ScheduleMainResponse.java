package com.example.myplant.adapter.in.web.response;

import com.example.myplant.domain.Schedule;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ScheduleMainResponse(
    Long scheduleId,
    String title,
    Boolean isCompleted,
    LocalDateTime startDateTime,
    LocalDateTime endDateTime,
    String colorType,
    Integer frequency,
String endDateTimeMessage
) {

    public static ScheduleMainResponse from(LocalDateTime now, Schedule schedule) {
        return ScheduleMainResponse.builder()
            .scheduleId(schedule.getScheduleId())
            .title(schedule.getTitle())
            .startDateTime(schedule.getStartDateTime())
            .endDateTime(schedule.getEndDateTime())
            .isCompleted(schedule.getIsCompleted())
            .colorType(schedule.getColorType())
            .frequency(schedule.getFrequency())
            .endDateTimeMessage(schedule.getDdayMessage(now))
            .build();
    }

}
