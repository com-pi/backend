package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.ArticleHashtagEntity;
import com.example.boardservice.application.port.out.ArticleHashtagCommandPort;
import com.example.boardservice.domain.ArticleHashtag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleHashtagCommandAdapter implements ArticleHashtagCommandPort {

    private final ArticleHashtagRepository articleHashtagRepository;

    @Override
    public void save(ArticleHashtag articleHashtag) {
        articleHashtagRepository.save(ArticleHashtagEntity.fromDomain(articleHashtag));
    }

    @Override
    public void deleteAllByArticleId(Long articleId) {
        articleHashtagRepository.deleteByArticleId(articleId);
    }
}
