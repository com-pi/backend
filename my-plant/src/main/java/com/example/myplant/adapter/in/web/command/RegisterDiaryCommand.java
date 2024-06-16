package com.example.myplant.adapter.in.web.command;

import com.example.myplant.domain.Diary;
import com.example.myplant.domain.PlantCare;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class RegisterDiaryCommand {

    private Long myPlantId;

    private Long memberId;

    private String title;

    private String content;

    private LocalDate createdDate;

    private List<PlantCare> plantCareList;

    private Boolean isPublished;

    private Boolean isPublic;

    public void addMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Diary toDomain() {
        return Diary.builder()
                .myPlantId(this.myPlantId)
                .memberId(this.memberId)
                .title(this.title)
                .content(this.content)
                .createdDate(this.createdDate)
                .plantCareList(this.plantCareList)
                .isPublished(this.isPublished)
                .isPublic(this.isPublic)
                .build();
    }

}
