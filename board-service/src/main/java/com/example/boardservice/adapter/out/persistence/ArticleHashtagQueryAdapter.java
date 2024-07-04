package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.ArticleHashtagEntity;
import com.example.boardservice.application.port.out.ArticleHashtagQueryPort;
import com.example.boardservice.domain.ArticleHashtag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public List<ArticleHashtag> getArticleIdByHashtagId(Long hashtagId, Pageable pageable) {
        Page<ArticleHashtagEntity> articleHashtagEntityList = articleHashtagRepository
                .findByHashtag_HashtagId(hashtagId, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
        return articleHashtagEntityList.stream()
                .map(ArticleHashtagEntity::toDomain)
                .toList();
    }
}
