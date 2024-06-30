package com.example.boardservice.adapter.in.web.command;

import com.example.boardservice.adapter.in.web.request.UpdateCommentRequest;
import com.example.boardservice.domain.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateCommentCommand {

    private Long commentId;
    private Long memberId;
    private String content;

    public static UpdateCommentCommand of(UpdateCommentRequest request, Long commentId, Long memberId) {
        return UpdateCommentCommand.builder()
                .commentId(commentId)
                .memberId(memberId)
                .content(request.getContent())
                .build();
    }

    public Comment toDomain() {
        return Comment.builder()
                .commentId(commentId)
                .memberId(memberId)
                .content(content)
                .build();
    }

}
