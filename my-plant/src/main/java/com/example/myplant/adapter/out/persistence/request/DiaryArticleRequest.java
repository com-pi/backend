package com.example.myplant.adapter.out.persistence.request;

import com.example.myplant.domain.Diary;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DiaryArticleRequest {

    private final Long memberId;
    private final String title;
    private final String content;
    private final List<String> imageUrlList;
    private final List<String> hashtagList;

    public static DiaryArticleRequest of(Diary diary, String plantName) {
        return DiaryArticleRequest.builder()
                .memberId(diary.getMemberId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .imageUrlList(diary.getImageUrlList())
                .hashtagList(List.of(plantName))
                .build();
    }

}
