package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.ArticleHashtag;

import java.util.List;

public interface ArticleHashtagQueryPort {
    List<ArticleHashtag> getArticleHashtagListByArticleId(Long articleId);
}
