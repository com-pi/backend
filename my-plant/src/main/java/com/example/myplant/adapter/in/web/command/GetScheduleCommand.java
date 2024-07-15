package com.example.myplant.adapter.in.web.command;


import com.example.myplant.domain.Schedule;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetScheduleCommand {
    private Long memberId;

    public static GetScheduleCommand of(Long memberId) {
        return GetScheduleCommand.builder()
                .memberId(memberId)
                .build();
    }

    public Schedule toDomain() {
        return Schedule.builder()
                .memberId(memberId)
                .build();
    }
}
