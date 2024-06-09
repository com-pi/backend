package com.example.boardservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {

    private Long articleId;

    private Long memberId;

    private String title;

    private String content;

    private Integer viewCount;

    private List<String> imageUrls;

    private ArticleType articleType;

    public static Article from(Long articleId) {
        return Article.builder()
                .articleId(articleId)
                .build();
    }
}
