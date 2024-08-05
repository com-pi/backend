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

    public static List<Long> getParentIdList(List<CommentWithReplies> commentList) {
        return commentList.stream()
                .map(commentWithReplies -> commentWithReplies.getComment().getCommentId())
                .toList();
    }

    public static List<Long> getChildIdList(List<CommentWithReplies> commentList) {
        return commentList.stream()
                .flatMap(commentWithReplies -> commentWithReplies.getChildren().stream())
                .map(Comment::getCommentId)
                .toList();
    }

}
