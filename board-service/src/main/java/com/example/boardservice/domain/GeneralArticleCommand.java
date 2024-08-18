package com.example.boardservice.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GeneralArticleCommand {

    private Long memberId;
    private Long articleId;
    private final String title;
    private final String content;
    private final List<String> hashtagList;
    private List<String> imageUrls;

    public GeneralArticle toDomain() {
        return GeneralArticle.builder()
                .member(Member.ofId(memberId))
                .articleId(articleId)
                .title(title)
                .content(content)
                .imageUrls(imageUrls)
                .viewCount(0)
                .type(ArticleType.GENERAL_BOARD)
                .build();
    }

    public void updateImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
