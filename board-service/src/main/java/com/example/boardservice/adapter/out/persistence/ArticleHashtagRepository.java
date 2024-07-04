package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.ArticleHashtagEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleHashtagRepository extends JpaRepository<ArticleHashtagEntity, Long> {

    void deleteByArticleId(Long articleId);

    List<ArticleHashtagEntity> findByArticleId(Long articleId);

    Page<ArticleHashtagEntity> findByHashtag_HashtagId(Long hashtagId, Pageable pageable);
}
