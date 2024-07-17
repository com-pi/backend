package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.Comment;

import java.util.List;

public interface CommentQueryPort {

    Comment getComment(Long commentId);

    List<Comment> getCommentList(Long articleId);

    int getCommentCount(Long articleId);
}
