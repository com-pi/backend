package com.example.myplant.adapter.in.web.command;

import com.example.myplant.domain.Diary;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetPersonalDiaryCommand {
    private Long diaryId;
    private Long memberId;

    public static GetPersonalDiaryCommand of(Long diaryId, Long memberId) {
        return GetPersonalDiaryCommand.builder()
                .diaryId(diaryId)
                .memberId(memberId)
                .build();
    }

    public Diary toDomain() {
        return Diary.builder()
                .diaryId(this.diaryId)
                .memberId(this.memberId)
                .build();
    }
}
