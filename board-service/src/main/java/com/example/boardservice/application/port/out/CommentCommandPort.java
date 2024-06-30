package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.Comment;

public interface CommentCommandPort {
    Comment save(Comment comment);

    Comment saveReply(Comment comment);

    void update(Comment comment);

    void delete(Comment comment);


}
