package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.GeneralArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GeneralArticleQueryPort {
    GeneralArticle getArticle(Long articleId);

    Page<GeneralArticle> getArticleList(Pageable pageable);
}
