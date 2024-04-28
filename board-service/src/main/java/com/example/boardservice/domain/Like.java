package com.example.boardservice.domain;

import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "generate")
public class Like {

    private Long likeId;
    private Long boardId;
    private Long userId;
    private Boolean isLiked;

}
