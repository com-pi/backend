package com.example.boardservice.domain;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Like {

    private Long likeId;

    private Long articleId;

    private Long memberId;

    private Boolean isLiked;

    private ArticleType articleType;

    /**
     * 생성자
     */
    public Like(Long likeId, Boolean isLiked) {
        this.likeId = likeId;
        this.isLiked = isLiked;
    }

    /**
     *
     */
    public void generate(ArticleType articleType) {
        this.articleType = articleType;
        this.isLiked = true;
    }

    public static Like ofStatus(Long likeId, Boolean isLiked) {
        return new Like(likeId, isLiked);
    }

    public void like() {
        this.isLiked = true;
    }

    public void unlike() {
        this.isLiked = false;
    }
}
