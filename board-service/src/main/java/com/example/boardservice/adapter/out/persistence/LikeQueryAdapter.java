package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.LikeEntity;
import com.example.boardservice.application.port.out.LikeQueryPort;
import com.example.boardservice.domain.Like;
import com.example.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LikeQueryAdapter implements LikeQueryPort {

    private final LikeRepository likeRepository;

    @Override
    public Like getLikeByLikeId(Long likeId) {
        LikeEntity likeEntity = likeRepository.findById(likeId)
                .orElseThrow(() -> new NotFoundException(LikeEntity.class));
        return likeEntity.toDomain();
    }

    @Override
    public Optional<Like> findLikeByArticleIdAndMemberId(Long articleId, Long memberId) {
        Optional<LikeEntity> likeEntity = likeRepository.findByArticle_ArticleIdAndMember_MemberId(articleId, memberId);
        return likeEntity.map(LikeEntity::toDomain);
    }

    @Override
    public Optional<Like> findLikeByArticleIdAndMemberIdAndIsLiked(Long articleId, Long memberId, boolean isLiked) {
        Optional<LikeEntity> likeEntity = likeRepository.findByArticle_ArticleIdAndMember_MemberIdAndIsLiked(articleId, memberId, isLiked);
        return likeEntity.map(LikeEntity::toDomain);
    }

}