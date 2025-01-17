package com.example.myplant.adapter.in.web.command;

import com.example.myplant.domain.Diary;
import com.example.myplant.domain.PlantCare;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UpdateDiaryCommand {

    private Long diaryId;

    private Long memberId;

    private String title;

    private String content;

    private List<PlantCare> plantCareList;

    private Boolean isPublished;

    private Boolean isPublic;

    public void addMemberId(Long memberId) {
        this.memberId = memberId;
    }


    public Diary toDomain() {
        return Diary.builder()
                .diaryId(this.diaryId)
                .memberId(this.memberId)
                .title(this.title)
                .content(this.content)
                .plantCareList(this.plantCareList)
                .isPublished(this.isPublished)
                .isPublic(this.isPublic)
                .build();
    }

}