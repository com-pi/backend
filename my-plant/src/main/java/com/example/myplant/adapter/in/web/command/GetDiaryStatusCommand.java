package com.example.myplant.adapter.in.web.command;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class GetDiaryStatusCommand {
    private LocalDate startDate;
    private LocalDate endDate;
    private Long myPlantId;
    private Long memberId;

    public static GetDiaryStatusCommand of(LocalDate startDate, LocalDate endDate, Long myPlantId, Long memberId) {
        return GetDiaryStatusCommand.builder()
                .startDate(startDate)
                .endDate(endDate)
                .myPlantId(myPlantId)
                .memberId(memberId)
                .build();
    }
}
