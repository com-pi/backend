package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.QnaArticle;

public interface QnaArticleCommandPort {
    QnaArticle save(QnaArticle domain);

    void update(QnaArticle domain);

    void delete(QnaArticle article);
}
