package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.CommonArticleEntity;
import com.example.boardservice.adapter.out.persistence.entity.GeneralArticleEntity;
import com.example.boardservice.application.port.out.CommonArticleQueryPort;
import com.example.boardservice.domain.Article;
import com.example.boardservice.domain.ArticleType;
import com.example.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommonArticleQueryAdapter implements CommonArticleQueryPort {

    private final CommonArticleRepository articleRepository;

    @Override
    public Page<Article> getArticleList(Pageable pageable) {
        Page<CommonArticleEntity> commonArticleEntityPage = articleRepository.findByDeletionYn("N", pageable);
        return commonArticleEntityPage.map(CommonArticleEntity::toArticle);
    }

    @Override
    public Page<Article> getArticleList(ArticleType type, Pageable pageable) {
        Page<CommonArticleEntity> commonArticleEntityPage = articleRepository.findByTypeAndDeletionYn(type, "N", pageable);
        return commonArticleEntityPage.map(CommonArticleEntity::toArticle);
    }

    @Override
    public List<Article> getArticleListByArticleId(List<Long> articleIdList, Pageable pageable) {
        List<CommonArticleEntity> commonArticleEntityList = articleRepository.findByArticleIdIn(articleIdList, pageable.getSort());
        return commonArticleEntityList.stream()
                .map(CommonArticleEntity::toArticle)
                .toList();
    }

    @Override
    public Article getArticle(Long articleId) {
        CommonArticleEntity entity = articleRepository.findByArticleIdAndDeletionYn(articleId, "N")
                .orElseThrow(() -> new NotFoundException(GeneralArticleEntity.class));
        return entity.toArticle();
    }

    @Override
    public Page<Article> searchArticleList(String keyword, Pageable pageable) {
        Page<CommonArticleEntity> commonArticleEntityPage = articleRepository.searchArticleList(keyword, "N", pageable);
        return commonArticleEntityPage.map(CommonArticleEntity::toArticle);
    }

    @Override
    public Page<Article> searchArticleList(String keyword, ArticleType type, Pageable pageable) {
        Page<CommonArticleEntity> commonArticleEntityPage = articleRepository.searchArticleListWithType(keyword, type, "N", pageable);
        return commonArticleEntityPage.map(CommonArticleEntity::toArticle);
    }


}
