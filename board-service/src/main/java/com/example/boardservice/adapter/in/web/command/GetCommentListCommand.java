package com.example.boardservice.adapter.in.web.command;

import com.example.boardservice.domain.Comment;
import com.example.boardservice.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetCommentListCommand {

    private final Long articleId;
    private final Long memberId;

    public static GetCommentListCommand of(Long articleId, Long memberId) {
        return GetCommentListCommand.builder()
                .articleId(articleId)
                .memberId(memberId)
                .build();
    }

    public Comment toDomain() {
        return Comment.builder()
                .articleId(articleId)
                .member(Member.ofId(memberId))
                .build();
    }
}
