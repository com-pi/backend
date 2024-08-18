package com.example.boardservice.adapter.in.web.command;

import com.example.boardservice.domain.GeneralArticle;
import com.example.boardservice.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteGeneralArticleCommand {

    private Long memberId;
    private final Long articleId;

    public static DeleteGeneralArticleCommand of(Long memberId, Long articleId) {
        return DeleteGeneralArticleCommand.builder()
                .memberId(memberId)
                .articleId(articleId)
                .build();
    }

    public GeneralArticle toDomain() {
        return GeneralArticle.builder()
                .member(Member.ofId(memberId))
                .articleId(articleId)
                .build();
    }

}
