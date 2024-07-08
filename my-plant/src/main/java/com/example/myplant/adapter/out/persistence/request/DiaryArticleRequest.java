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

    public static DiaryArticleRequest of(Diary diary) {
        return DiaryArticleRequest.builder()
                .memberId(diary.getMemberId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .imageUrlList(diary.getImageUrlList())
                .build();
    }

}
