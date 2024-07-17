package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.CommonArticleEntity;
import com.example.boardservice.application.port.out.CommonArticleCommandPort;
import com.example.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonArticleCommandAdapter implements CommonArticleCommandPort {

    private final CommonArticleRepository articleRepository;

    @Override
    public void increaseLikeCount(Long articleId) {
        CommonArticleEntity articleEntity = articleRepository.findByArticleId(articleId)
                        .orElseThrow(() -> new NotFoundException(CommonArticleEntity.class));
        articleEntity.increaseLikeCount();
    }

    @Override
    public void decreaseLikeCount(Long articleId) {
        CommonArticleEntity articleEntity = articleRepository.findByArticleId(articleId)
                .orElseThrow(() -> new NotFoundException(CommonArticleEntity.class));
        articleEntity.decreaseLikeCount();
    }

    @Override
    public void increaseCommentCount(Long articleId) {
        CommonArticleEntity articleEntity = articleRepository.findByArticleId(articleId)
                .orElseThrow(() -> new NotFoundException(CommonArticleEntity.class));
        articleEntity.increaseCommentCount();
    }

    @Override
    public void decreaseCommentCount(Long articleId) {
        CommonArticleEntity articleEntity = articleRepository.findByArticleId(articleId)
                .orElseThrow(() -> new NotFoundException(CommonArticleEntity.class));
        articleEntity.decreaseCommentCount();
    }
}
