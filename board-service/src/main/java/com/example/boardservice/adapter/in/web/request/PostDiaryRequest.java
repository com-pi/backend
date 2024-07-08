package com.example.boardservice.adapter.in.web.request;

import com.example.boardservice.domain.DiaryArticle;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostDiaryRequest {

    private final Long memberId;
    private final String title;
    private final String content;
    private final List<String> imageUrlList;

    public DiaryArticle toDomain() {
        return DiaryArticle.builder()
                .memberId(memberId)
                .title(title)
                .content(content)
                .imageUrls(imageUrlList)
                .build();
    }

}
