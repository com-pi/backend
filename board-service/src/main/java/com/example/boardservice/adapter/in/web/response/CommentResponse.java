package com.example.boardservice.adapter.in.web.response;

import com.example.boardservice.domain.Comment;

import java.time.LocalDate;

public record CommentResponse(
        Long commentId,
        Long parentId,
        Long memberId,
        String content,
        LocalDate createdDate,
        Boolean isEditable,
        Boolean isLiked,
        Integer likeCount
) {
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getCommentId(),
                comment.getParent() != null ? comment.getParent().getCommentId() : null,
                comment.getMemberId(),
                comment.getContent(),
                comment.getCreatedDate(),
                comment.getIsEditable(),
                comment.getIsLiked(),
                comment.getLikeCount()
        );
    }
}
