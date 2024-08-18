package com.example.myplant.adapter.in.web.command;

import com.example.myplant.domain.Schedule;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteScheduleCommand {

    private Long scheduleId;
    private Long memberId;

    public static DeleteScheduleCommand of(Long scheduleId, Long memberId) {
        return DeleteScheduleCommand.builder()
                .scheduleId(scheduleId)
                .memberId(memberId)
                .build();
    }

    public Schedule toDomain() {
        return Schedule.builder()
                .scheduleId(this.scheduleId)
                .memberId(this.memberId)
                .build();
    }
}
