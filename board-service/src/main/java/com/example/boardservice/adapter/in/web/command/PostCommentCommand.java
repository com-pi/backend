package com.example.boardservice.adapter.in.web.command;

import com.example.boardservice.adapter.in.web.request.PostCommentRequest;
import com.example.boardservice.domain.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class PostCommentCommand {

    private Long memberId;
    private final String content;
    private final Long articleId;

    public static PostCommentCommand of(PostCommentRequest request, Long memberId) {
        return PostCommentCommand.builder()
                .memberId(memberId)
                .content(request.getContent())
                .articleId(request.getArticleId())
                .build();
    }

    public Comment toDomain() {
        return Comment.builder()
                .memberId(memberId)
                .content(content)
                .articleId(articleId)
                .likeCount(0)
                .createdDate(LocalDate.now())
                .build();
    }
}
