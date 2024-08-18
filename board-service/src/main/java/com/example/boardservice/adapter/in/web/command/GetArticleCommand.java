package com.example.boardservice.adapter.in.web.command;

import com.example.boardservice.domain.Article;
import com.example.boardservice.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetArticleCommand {

    private Long memberId;
    private Long articleId;

    public static GetArticleCommand of(Long memberId, Long articleId) {
        return GetArticleCommand.builder()
                .memberId(memberId)
                .articleId(articleId)
                .build();
    }

    public Article toDomain() {
        return Article.builder()
                .member(Member.ofId(this.memberId))
                .articleId(this.articleId)
                .build();
    }

}
