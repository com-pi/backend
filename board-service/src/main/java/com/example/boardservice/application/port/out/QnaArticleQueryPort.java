package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.QnaArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QnaArticleQueryPort {
    QnaArticle getArticle(Long articleId);

    Page<QnaArticle> getArticleList(Pageable pageable);
}
