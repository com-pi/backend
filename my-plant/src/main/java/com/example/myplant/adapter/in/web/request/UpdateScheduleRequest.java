package com.example.myplant.adapter.in.web.request;

import com.example.myplant.domain.Schedule;

import java.time.LocalDateTime;

public record UpdateScheduleRequest(
    String title,
    LocalDateTime startDateTime,
    LocalDateTime endDateTime,
    Integer frequency,
    String colorType
) {
    public Schedule toDomain(Long scheduleId, Long memberId) {
        return Schedule.builder()
            .scheduleId(scheduleId)
            .title(title)
            .memberId(memberId)
            .startDateTime(startDateTime)
            .endDateTime(endDateTime)
            .frequency(frequency)
            .colorType(colorType)
            .isRecurring(frequency != 0)
            .build();
    }
}
