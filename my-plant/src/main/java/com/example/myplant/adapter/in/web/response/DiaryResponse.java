package com.example.myplant.adapter.in.web.response;

import com.example.myplant.domain.Diary;
import com.example.myplant.domain.PlantCare;

import java.util.List;

public record DiaryResponse(
        Long diaryId,
        Long myPlantId,
        List<PlantCare> plantCareList,
        String title,
        String content,
        List<String> imageUrlList
) {
    public static DiaryResponse from(Diary diary) {
        return new DiaryResponse(
                diary.getDiaryId(),
                diary.getMyPlantId(),
                diary.getPlantCareList(),
                diary.getTitle(),
                diary.getContent(),
                diary.getImageUrlList()
        );
    }
}
