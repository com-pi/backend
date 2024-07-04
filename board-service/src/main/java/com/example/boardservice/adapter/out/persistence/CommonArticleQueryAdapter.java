package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.CommonArticleEntity;
import com.example.boardservice.application.port.out.CommonArticleQueryPort;
import com.example.boardservice.domain.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommonArticleQueryAdapter implements CommonArticleQueryPort {

    private final CommonArticleRepository commonArticleRepository;

    @Override
    public Page<Article> getArticleList(Pageable pageable) {
        Page<CommonArticleEntity> commonArticleEntityPage = commonArticleRepository.findByDeletionYn("N", pageable);
        return commonArticleEntityPage.map(CommonArticleEntity::toArticle);
    }

    @Override
    public List<Article> getArticleListByArticleId(List<Long> articleIdList, Pageable pageable) {
        List<CommonArticleEntity> commonArticleEntityList = commonArticleRepository.findByArticleIdIn(articleIdList, pageable.getSort());
        return commonArticleEntityList.stream()
                .map(CommonArticleEntity::toArticle)
                .toList();
    }

}
