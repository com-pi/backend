package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.CommonArticleEntity;
import com.example.boardservice.domain.ArticleHashtag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommonArticleRepository extends JpaRepository<CommonArticleEntity, Long> {
    Page<CommonArticleEntity> findByDeletionYn(String deletionYn, Pageable pageable);

    List<CommonArticleEntity> findByArticleIdInOrderByCreatedAt(List<ArticleHashtag> articleIdList);

    List<CommonArticleEntity> findByArticleIdInOrderByCreatedAtDesc(List<ArticleHashtag> articleIdList);

    List<CommonArticleEntity> findByArticleIdIn(List<Long> articleIdList, Sort sort);
}
