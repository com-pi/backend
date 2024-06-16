package com.example.myplant.adapter.in.web.command;

import com.example.myplant.domain.Diary;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class GetDiaryCommand {
    private LocalDate createdDate;
    private Long memberId;

    public static GetDiaryCommand of(LocalDate createdDate, Long memberId) {
        return GetDiaryCommand.builder()
                .createdDate(createdDate)
                .memberId(memberId)
                .build();
    }

    public Diary toDomain() {
        return Diary.builder()
                .createdDate(this.createdDate)
                .memberId(this.memberId)
                .build();
    }
}
