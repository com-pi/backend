package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.ArticleLike;

import java.util.List;
import java.util.Optional;

public interface ArticleLikeQueryPort {

    ArticleLike getLikeByArticleIdAndMemberId(Long articleId, Long memberId);

    Optional<ArticleLike> findLikeByArticleIdAndMemberId(Long articleId, Long memberId);

    Optional<ArticleLike> findLikeByArticleIdAndMemberIdAndIsLiked(Long articleId, Long memberId, boolean isLiked);

    List<ArticleLike> getLikeByArticleList(List<Long> articleIdList, Long memberId);

}
