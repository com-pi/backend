package com.example.boardservice.application.port.in;

import com.example.boardservice.domain.ArticleLike;

public interface ArticleLikeUseCase {
    Long like(ArticleLike like);

    Long unlike(ArticleLike like);

    ArticleLike getLikeStatusByCurrentMember(Long articleId, Long memberId);
}
