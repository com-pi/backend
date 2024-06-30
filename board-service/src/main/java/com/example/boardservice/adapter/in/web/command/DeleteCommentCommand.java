package com.example.boardservice.adapter.in.web.command;

import com.example.boardservice.domain.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteCommentCommand {

    private Long memberId;
    private final Long commentId;

    public static DeleteCommentCommand of(Long memberId, Long commentId) {
        return DeleteCommentCommand.builder()
                .memberId(memberId)
                .commentId(commentId)
                .build();
    }

    public Comment toDomain() {
        return Comment.builder()
                .memberId(memberId)
                .commentId(commentId)
                .build();
    }
}
