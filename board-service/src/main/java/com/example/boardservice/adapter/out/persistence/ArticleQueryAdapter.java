package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.CommonArticleEntity;
import com.example.boardservice.application.port.out.ArticleQueryPort;
import com.example.boardservice.domain.Article;
import com.example.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleQueryAdapter implements ArticleQueryPort {

    private final ArticleRepository articleRepository;

    @Override
    public Article getArticleById(Long articleId) {
        CommonArticleEntity articleEntity = articleRepository.findById(articleId)
                .orElseThrow(() -> new NotFoundException(CommonArticleEntity.class));
        return articleEntity.toArticle();
    }
}
