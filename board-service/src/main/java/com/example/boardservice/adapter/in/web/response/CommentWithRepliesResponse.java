package com.example.boardservice.adapter.in.web.response;

import com.example.boardservice.domain.CommentWithReplies;

import java.util.List;

public record CommentWithRepliesResponse(
        CommentResponse comment,
        List<CommentResponse> children
) {
    public static CommentWithRepliesResponse from(CommentWithReplies commentWithReplies) {
        return new CommentWithRepliesResponse(
            CommentResponse.from(commentWithReplies.getComment()),
            commentWithReplies.getChildren().stream().map(CommentResponse::from).toList()
        );
    }
}
