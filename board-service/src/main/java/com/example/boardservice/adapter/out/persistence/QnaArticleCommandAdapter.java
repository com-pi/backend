package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.QnaArticleEntity;
import com.example.boardservice.application.port.out.QnaArticleCommandPort;
import com.example.boardservice.domain.QnaArticle;
import com.example.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QnaArticleCommandAdapter implements QnaArticleCommandPort {

    private final QnaArticleRepository qnaArticleRepository;

    @Override
    public QnaArticle save(QnaArticle article) {
        QnaArticleEntity qnaArticleEntity = qnaArticleRepository.save(QnaArticleEntity.fromDomain(article));
        return qnaArticleEntity.toDomain();
    }

    @Override
    public void update(QnaArticle article) {
        QnaArticleEntity qnaArticleEntity = qnaArticleRepository.findById(article.getArticleId())
                .orElseThrow(() -> new NotFoundException(QnaArticleEntity.class));
        qnaArticleEntity.update(article);
    }

    @Override
    public void delete(QnaArticle article) {
        QnaArticleEntity qnaArticleEntity = qnaArticleRepository.findById(article.getArticleId())
                .orElseThrow(() -> new NotFoundException(QnaArticleEntity.class));
        qnaArticleEntity.delete();
    }
}
