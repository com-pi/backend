package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.ArticleHashtagEntity;
import com.example.boardservice.application.port.out.ArticleHashtagQueryPort;
import com.example.boardservice.domain.ArticleHashtag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ArticleHashtagQueryAdapter implements ArticleHashtagQueryPort {

    private final ArticleHashtagRepository articleHashtagRepository;

    @Override
    public List<ArticleHashtag> getArticleHashtagListByArticleId(Long articleId) {
        return articleHashtagRepository.findByArticleId(articleId).stream()
                .map(ArticleHashtagEntity::toDomain)
                .toList();
    }
}
