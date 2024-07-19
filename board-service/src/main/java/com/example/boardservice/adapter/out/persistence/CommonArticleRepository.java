package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.CommonArticleEntity;
import com.example.boardservice.domain.ArticleHashtag;
import com.example.boardservice.domain.ArticleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommonArticleRepository extends JpaRepository<CommonArticleEntity, Long> {

    Page<CommonArticleEntity> findByDeletionYn(String deletionYn, Pageable pageable);

    Page<CommonArticleEntity> findByTypeAndDeletionYn(ArticleType type, String deletionYn, Pageable pageable);

    List<CommonArticleEntity> findByArticleIdInOrderByCreatedAt(List<ArticleHashtag> articleIdList);

    List<CommonArticleEntity> findByArticleIdInOrderByCreatedAtDesc(List<ArticleHashtag> articleIdList);

    List<CommonArticleEntity> findByArticleIdIn(List<Long> articleIdList, Sort sort);

    Optional<CommonArticleEntity> findByArticleIdAndDeletionYn(Long articleId, String deletionYn);

    Optional<CommonArticleEntity> findByArticleId(Long articleId);

}
