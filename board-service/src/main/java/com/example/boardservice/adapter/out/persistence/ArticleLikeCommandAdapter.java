package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.ArticleLikeEntity;
import com.example.boardservice.application.port.out.ArticleLikeCommandPort;
import com.example.boardservice.domain.ArticleLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ArticleLikeCommandAdapter implements ArticleLikeCommandPort {

    private final ArticleLikeRepository likeRepository;

    @Override
    @Transactional
    public ArticleLike save(ArticleLike like) {
        ArticleLikeEntity likeEntity = likeRepository.save(ArticleLikeEntity.from(like));
        return likeEntity.toDomain();
    }
}
