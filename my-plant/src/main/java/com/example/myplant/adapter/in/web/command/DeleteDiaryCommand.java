package com.example.myplant.adapter.in.web.command;

import com.example.myplant.domain.Diary;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteDiaryCommand {

    private Long diaryId;

    private Long memberId;

    public static DeleteDiaryCommand of(Long diaryId, Long memberId) {
        return DeleteDiaryCommand.builder()
                .diaryId(diaryId)
                .memberId(memberId)
                .build();
    }

    public Diary toDomain() {
        return Diary.builder()
                .diaryId(diaryId)
                .memberId(memberId)
                .build();
    }

}
