package com.example.boardservice.adapter.in.web.command;

import com.example.boardservice.domain.Member;
import com.example.boardservice.domain.QnaArticle;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteQnaArticleCommand {

    private Long memberId;
    private final Long articleId;

    public static DeleteQnaArticleCommand of(Long memberId, Long articleId) {
        return DeleteQnaArticleCommand.builder()
                .memberId(memberId)
                .articleId(articleId)
                .build();
    }

    public QnaArticle toDomain() {
        return QnaArticle.builder()
                .member(Member.ofId(memberId))
                .articleId(articleId)
                .build();
    }

}
