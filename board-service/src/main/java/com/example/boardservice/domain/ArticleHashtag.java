package com.example.boardservice.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ArticleHashtag {

    private Long articleHashtagId;

    private Long articleId;

    private Hashtag hashtag;

}
