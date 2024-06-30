package com.example.boardservice.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CommentWithReplies {

    private Comment comment;
    private List<Comment> children;

    public static CommentWithReplies of(Comment comment, List<Comment> children) {
        return CommentWithReplies.builder()
                .comment(comment)
                .children(children)
                .build();
    }

}
