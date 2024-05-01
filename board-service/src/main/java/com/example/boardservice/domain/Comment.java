package com.example.boardservice.domain;

import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "generate")
public class Comment {

    private Long boardId;
    private Integer dept;
    private Long parentCommentId;
    private Long commentId;
    private Long authorId;
    private String content;

}