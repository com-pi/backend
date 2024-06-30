package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByLikeIdAndIsLiked(Long likeId, Boolean isLiked);

    Optional<LikeEntity> findByArticle_ArticleIdAndMemberId(Long articleId, Long memberId);

    Optional<LikeEntity> findByArticle_ArticleIdAndMemberIdAndIsLiked(Long articleId, Long memberId, boolean isLiked);
}
