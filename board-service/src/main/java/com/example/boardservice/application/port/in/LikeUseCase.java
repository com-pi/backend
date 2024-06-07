package com.example.boardservice.application.port.in;

import com.example.boardservice.domain.Like;

public interface LikeUseCase {
    Long like(Like like);

    Long unlike(Like like);

    Like getLikeStatusByCurrentMember(Long articleId, Long memberId);
}
