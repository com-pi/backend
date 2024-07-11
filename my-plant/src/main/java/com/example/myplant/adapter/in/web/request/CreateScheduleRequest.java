package com.example.myplant.adapter.in.web.request;

import com.example.myplant.domain.Schedule;

import java.time.LocalDateTime;
import java.util.Objects;

public record CreateScheduleRequest(
    String title,
    LocalDateTime startDateTime,
    LocalDateTime endDateTime,
    Integer frequency,
    String colorType
) {
    public Schedule toDomain(long memberId) {
        return Schedule.builder()
            .title(title)
            .memberId(memberId)
            .startDateTime(startDateTime)
            .endDateTime(endDateTime)
            .frequency(frequency)
            .colorType(colorType)
            .isCompleted(false)
            .isRecurring(Objects.nonNull(frequency))
            .build();
    }
}
