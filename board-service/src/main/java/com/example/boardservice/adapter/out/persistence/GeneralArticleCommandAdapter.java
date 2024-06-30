package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.GeneralArticleEntity;
import com.example.boardservice.application.port.out.GeneralArticleCommandPort;
import com.example.boardservice.domain.GeneralArticle;
import com.example.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GeneralArticleCommandAdapter implements GeneralArticleCommandPort {

    private final GeneralArticleRepository generalArticleRepository;
    @Override
    public GeneralArticle save(GeneralArticle article) {
        GeneralArticleEntity generalArticleEntity = generalArticleRepository.save(GeneralArticleEntity.fromDomain(article));
        return generalArticleEntity.toDomain();
    }

    @Override
    public void update(GeneralArticle article) {
        GeneralArticleEntity generalArticleEntity = generalArticleRepository.findById(article.getArticleId())
                .orElseThrow(() -> new NotFoundException(GeneralArticleEntity.class));
        generalArticleEntity.update(article);
    }

    @Override
    public void delete(GeneralArticle article) {
        GeneralArticleEntity generalArticleEntity = generalArticleRepository.findById(article.getArticleId())
                .orElseThrow(() -> new NotFoundException(GeneralArticleEntity.class));
        generalArticleEntity.delete();
    }
}
