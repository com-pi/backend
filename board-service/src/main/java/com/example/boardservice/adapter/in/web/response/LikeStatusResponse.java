package com.example.boardservice.adapter.in.web.response;

import com.example.boardservice.domain.ArticleLike;

public record LikeStatusResponse(
        Long likeId,
        boolean isLiked
) {
    public static LikeStatusResponse from(ArticleLike like) {
        return new LikeStatusResponse(
                like.getLikeId(),
                like.getIsLiked()
        );
    }
}
