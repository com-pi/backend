package com.example.boardservice.adapter.in.web.command;

import com.example.boardservice.domain.CommentLike;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LikeCommentCommand {
    private Long commentId;
    private Long memberId;

    public static LikeCommentCommand of(Long commentId, Long memberId) {
        return LikeCommentCommand.builder()
                .commentId(commentId)
                .memberId(memberId)
                .build();
    }

    public CommentLike toDomain() {
        return CommentLike.builder()
                .commentId(commentId)
                .memberId(memberId)
                .build();
    }
}