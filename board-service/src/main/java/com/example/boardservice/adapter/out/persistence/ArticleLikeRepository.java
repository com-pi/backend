package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.ArticleLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleLikeRepository extends JpaRepository<ArticleLikeEntity, Long> {
    Optional<ArticleLikeEntity> findByLikeIdAndIsLiked(Long likeId, Boolean isLiked);

    Optional<ArticleLikeEntity> findByArticle_ArticleIdAndMemberId(Long articleId, Long memberId);

    Optional<ArticleLikeEntity> findByArticle_ArticleIdAndMemberIdAndIsLiked(Long articleId, Long memberId, boolean isLiked);

    int countByArticle_ArticleId(Long articleId);

    List<ArticleLikeEntity> findByArticle_ArticleIdInAndMemberId(List<Long> articleIdList, Long memberId);

}
