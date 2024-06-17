package com.example.myplant.adapter.in.web.request;

import com.example.myplant.domain.Schedule;

import java.time.LocalDateTime;
public record UpdateScheduleRequest(
    String title,
    LocalDateTime endDateTime
) {
    public Schedule toDomain(Long scheduleId, Long memberId) {
        return Schedule.builder()
            .scheduleId(scheduleId)
            .title(title)
            .memberId(memberId)
            .endDateTime(endDateTime)
            .build();
    }
}
