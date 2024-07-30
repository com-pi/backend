package com.example.boardservice.application.port.in;

import com.example.boardservice.domain.Comment;
import com.example.boardservice.domain.CommentWithReplies;

import java.util.List;

public interface CommentUseCase {
    Long post(Comment comment);

    Long postReply(Comment comment);

    Long update(Comment comment);

    Long delete(Comment comment);

    List<CommentWithReplies> getCommentList(Comment comment);
}
