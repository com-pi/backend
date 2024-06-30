package com.example.boardservice.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ArticleHashtag {

    private Long articleHashtagId;

    private Long articleId;

    private Hashtag hashtag;

    public static ArticleHashtag generateArticleHashtag(Long articleId, Hashtag hashtag) {
        return ArticleHashtag.builder()
                .articleId(articleId)
                .hashtag(hashtag)
                .build();
    }

}
