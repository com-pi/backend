package com.example.boardservice.adapter.in.web.response;

import com.example.boardservice.domain.Comment;

import java.time.LocalDate;

public record CommentResponse(
        Long commentId,
        Long parentId,
        Long memberId,
        String content,
        LocalDate createdDate,
        boolean isEditable
) {
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getCommentId(),
                comment.getParent() != null ? comment.getParent().getCommentId() : null,
                comment.getMemberId(),
                comment.getContent(),
                comment.getCreatedDate(),
                comment.getIsEditable()
        );
    }
}
