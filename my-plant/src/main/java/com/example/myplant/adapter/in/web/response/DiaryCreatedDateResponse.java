package com.example.myplant.adapter.in.web.response;

import com.example.myplant.domain.Diary;

import java.time.LocalDate;

public record DiaryCreatedDateResponse(
        Long diaryId,
        LocalDate createdDate
) {
    public static DiaryCreatedDateResponse from(Diary diary) {
        return new DiaryCreatedDateResponse(
                diary.getDiaryId(),
                diary.getCreatedDate()
        );
    }
}
