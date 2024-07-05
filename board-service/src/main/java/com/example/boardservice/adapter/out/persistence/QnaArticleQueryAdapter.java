package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.QnaArticleEntity;
import com.example.boardservice.application.port.out.QnaArticleQueryPort;
import com.example.boardservice.domain.QnaArticle;
import com.example.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QnaArticleQueryAdapter implements QnaArticleQueryPort {
    
    private final QnaArticleRepository qnaArticleRepository;

    @Override
    public QnaArticle getArticle(Long articleId) {
        QnaArticleEntity qnaArticleEntity = qnaArticleRepository.findByArticleIdAndDeletionYn(articleId, "N")
                .orElseThrow(() -> new NotFoundException(QnaArticleEntity.class));
        return qnaArticleEntity.toDomain();
    }

    @Override
    public Page<QnaArticle> getArticleList(Pageable pageable) {
        Page<QnaArticleEntity> qnaArticleEntityPage = qnaArticleRepository.findByDeletionYn("N", pageable);
        return qnaArticleEntityPage.map(QnaArticleEntity::toDomain);
    }
}
