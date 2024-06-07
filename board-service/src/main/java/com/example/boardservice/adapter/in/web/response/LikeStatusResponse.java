package com.example.boardservice.adapter.in.web.response;

import com.example.boardservice.domain.Like;

public record LikeStatusResponse(
        Long likeId,
        boolean isLiked
) {
    public static LikeStatusResponse from(Like like) {
        return new LikeStatusResponse(
                like.getLikeId(),
                like.getIsLiked()
        );
    }
}
