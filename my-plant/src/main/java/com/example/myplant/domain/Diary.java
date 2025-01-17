package com.example.myplant.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diary {

    private Long diaryId;

    private Long memberId;

    private Long myPlantId;

    private String title;

    private String content;

    private LocalDate createdDate;

    private List<PlantCare> plantCareList;

    private List<String> imageUrlList;

    private Boolean isPublished;

    private Boolean isPublic;

    public void updateImageUrlList(List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }

    public boolean hasImageFiles(List<MultipartFile> imageFiles) {
        return imageFiles != null && !imageFiles.isEmpty();
    }

    public boolean isSyncable() {
        return this.isPublic && this.isPublished;
    }

}