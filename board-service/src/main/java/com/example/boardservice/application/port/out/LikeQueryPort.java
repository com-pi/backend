package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.Like;

import java.util.List;
import java.util.Optional;

public interface LikeQueryPort {
    Like getLikeByLikeId(Long likeId);

    Optional<Like> findLikeByArticleIdAndMemberId(Long articleId, Long memberId);

    Optional<Like> findLikeByArticleIdAndMemberIdAndIsLiked(Long articleId, Long memberId, boolean isLiked);

    int getLikeCount(Long articleId);

    List<Like> getLikeByArticleList(List<Long> articleIdList, Long memberId);

}
