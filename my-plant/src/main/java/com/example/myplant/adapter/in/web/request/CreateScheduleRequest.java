package com.example.myplant.adapter.in.web.request;

import com.example.myplant.domain.Schedule;

import java.time.LocalDateTime;
public record CreateScheduleRequest(
    String title,

    LocalDateTime endDateTime
) {
    public Schedule toDomain(long memberId) {
        return Schedule.builder()
            .title(title)
            .memberId(memberId)
            .endDateTime(endDateTime)
            .isCompleted(false)
            .build();
    }
}
