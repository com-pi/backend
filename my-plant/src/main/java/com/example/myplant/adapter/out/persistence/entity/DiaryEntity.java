package com.example.myplant.adapter.out.persistence.entity;

import com.example.common.baseentity.DeletedAtAbstractEntity;
import com.example.common.converter.JsonToStringListConverter;
import com.example.myplant.adapter.out.persistence.converter.PlantCareToListConverter;
import com.example.myplant.domain.Diary;
import com.example.myplant.domain.PlantCare;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "diary")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryEntity extends DeletedAtAbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryId;

    private Long memberId;

    private Long myPlantId;

    private String title;

    private String content;

    private LocalDate createdDate;

    @Convert(converter = PlantCareToListConverter.class)
    private List<PlantCare> plantCareList;

    @Convert(converter = JsonToStringListConverter.class)
    private List<String> imageUrlList;

    private Boolean isPublished;

    private Boolean isPublic;

    /**
     * 생성자
     */
    @Builder
    public DiaryEntity(Long diaryId, Long memberId, Long myPlantId, String title, String content, LocalDate createdDate, List<PlantCare> plantCareList, List<String> imageUrlList, Boolean isPublished, Boolean isPublic) {
        this.diaryId = diaryId;
        this.memberId = memberId;
        this.myPlantId = myPlantId;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.plantCareList = plantCareList;
        this.imageUrlList = imageUrlList;
        this.isPublished = isPublished;
        this.isPublic = isPublic;
    }

    /**
     *
     */
    public Diary toDomain() {
        return Diary.builder()
                .diaryId(this.diaryId)
                .memberId(this.memberId)
                .myPlantId(this.myPlantId)
                .title(this.title)
                .content(this.content)
                .createdDate(this.createdDate)
                .plantCareList(this.plantCareList)
                .imageUrlList(this.imageUrlList)
                .isPublished(this.isPublished)
                .isPublic(this.isPublic)
                .build();
    }

    public static DiaryEntity fromDomain(Diary diary) {
        return DiaryEntity.builder()
                .diaryId(diary.getDiaryId())
                .memberId(diary.getMemberId())
                .myPlantId(diary.getMyPlantId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .createdDate(diary.getCreatedDate())
                .plantCareList(diary.getPlantCareList())
                .imageUrlList(diary.getImageUrlList())
                .isPublished(diary.getIsPublished())
                .isPublic(diary.getIsPublic())
                .build();
    }

    public void update(Diary diary) {
        this.title = diary.getTitle();
        this.content = diary.getContent();
        this.plantCareList = diary.getPlantCareList();
        this.imageUrlList = diary.getImageUrlList();
        this.isPublished = diary.getIsPublished();
        this.isPublic = diary.getIsPublic();
    }

    @Override
    public void delete() {
        super.delete();
    }

}
