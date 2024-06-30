package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.GeneralArticleEntity;
import com.example.boardservice.application.port.out.GeneralArticleQueryPort;
import com.example.boardservice.domain.GeneralArticle;
import com.example.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GeneralArticleQueryAdapter implements GeneralArticleQueryPort {

    private final GeneralArticleRepository generalArticleRepository;

    @Override
    public GeneralArticle getArticle(Long articleId) {
        GeneralArticleEntity generalArticleEntity = generalArticleRepository.findByArticleIdAndDeletionYn(articleId, "N")
                .orElseThrow(() -> new NotFoundException(GeneralArticleEntity.class));
        return generalArticleEntity.toDomain();
    }

    @Override
    public Page<GeneralArticle> getArticleList(Pageable pageable) {
        Page<GeneralArticleEntity> generalArticleEntityPage = generalArticleRepository.findByDeletionYn("N", pageable);
        return generalArticleEntityPage.map(GeneralArticleEntity::toDomain);
    }
}
