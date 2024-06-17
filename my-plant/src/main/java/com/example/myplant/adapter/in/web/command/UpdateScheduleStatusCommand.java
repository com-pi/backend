package com.example.myplant.adapter.in.web.command;

import com.example.myplant.domain.Schedule;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateScheduleStatusCommand {
    private Long scheduleId;
    private Long memberId;

    public static UpdateScheduleStatusCommand of(Long scheduleId, Long memberId) {
        return UpdateScheduleStatusCommand.builder()
                .scheduleId(scheduleId)
                .memberId(memberId)
                .build();
    }

    public Schedule toDomain() {
        return Schedule.builder()
                .scheduleId(scheduleId)
                .memberId(memberId)
                .build();
    }
}
