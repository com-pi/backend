package com.example.boardservice.adapter.in.web.command;

import com.example.boardservice.domain.ArticleLike;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UnlikeArticleCommand {
    private Long articleId;
    private Long memberId;

    public static UnlikeArticleCommand of(Long articleId, Long memberId) {
        return UnlikeArticleCommand.builder()
                .articleId(articleId)
                .memberId(memberId)
                .build();
    }

    public ArticleLike toDomain() {
        return ArticleLike.builder()
                .articleId(articleId)
                .memberId(memberId)
                .build();
    }
}