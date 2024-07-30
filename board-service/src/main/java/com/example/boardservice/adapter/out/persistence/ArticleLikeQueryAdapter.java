package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.ArticleLikeEntity;
import com.example.boardservice.application.port.out.ArticleLikeQueryPort;
import com.example.boardservice.domain.ArticleLike;
import com.example.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ArticleLikeQueryAdapter implements ArticleLikeQueryPort {

    private final ArticleLikeRepository likeRepository;

    @Override
    public ArticleLike getLikeByArticleIdAndMemberId(Long articleId, Long memberId) {
        ArticleLikeEntity likeEntity = likeRepository.findByArticle_ArticleIdAndMemberId(articleId, memberId)
                .orElseThrow(() -> new NotFoundException(ArticleLikeEntity.class));
        return likeEntity.toDomain();
    }

    @Override
    public Optional<ArticleLike> findLikeByArticleIdAndMemberId(Long articleId, Long memberId) {
        Optional<ArticleLikeEntity> likeEntity = likeRepository.findByArticle_ArticleIdAndMemberId(articleId, memberId);
        return likeEntity.map(ArticleLikeEntity::toDomain);
    }

    @Override
    public Optional<ArticleLike> findLikeByArticleIdAndMemberIdAndIsLiked(Long articleId, Long memberId, boolean isLiked) {
        Optional<ArticleLikeEntity> likeEntity = likeRepository.findByArticle_ArticleIdAndMemberIdAndIsLiked(articleId, memberId, isLiked);
        return likeEntity.map(ArticleLikeEntity::toDomain);
    }

    @Override
    public List<ArticleLike> getLikeByArticleList(List<Long> articleIdList, Long memberId) {
        List<ArticleLikeEntity> likeEntityList =  likeRepository.findByArticle_ArticleIdInAndMemberId(articleIdList, memberId);
        return likeEntityList.stream()
                .map(ArticleLikeEntity::toDomain)
                .toList();
    }


}
