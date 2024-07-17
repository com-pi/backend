package com.example.boardservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Article {

    private Long articleId;

    private Long memberId;

    private String title;

    private String content;

    private Integer viewCount;

    private List<String> imageUrls;

    private List<String> hashtagList;

    private ArticleType articleType;

    private LocalDateTime createdAt;

    private int likeCount;

    private int commentCount;

    public void addHashtagList(List<String> hashtagList) {
        this.hashtagList = hashtagList;
    }

}
