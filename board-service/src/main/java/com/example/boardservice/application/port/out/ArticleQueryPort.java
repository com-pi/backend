package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.Article;

public interface ArticleQueryPort {
    Article getArticleById(Long articleId);
}
