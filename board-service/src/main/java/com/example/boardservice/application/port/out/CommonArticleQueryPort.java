package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.Article;
import com.example.boardservice.domain.ArticleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommonArticleQueryPort {
    Page<Article> getArticleList( Pageable pageable);

    Page<Article> getArticleList(ArticleType type, Pageable pageable);

    List<Article> getArticleListByArticleId(List<Long> articleIdList, String type, Pageable pageable);

    Article getArticle(Long articleId);

    Page<Article> searchArticleList(String keyword, Pageable pageable);

    Page<Article> searchArticleList(String keyword, ArticleType type, Pageable pageable);

    Page<Article> getArticleListByMemberId(Long memberId, Pageable pageable);

    Page<Article> getLikedArticleId(Long memberId, Pageable pageable);
}
