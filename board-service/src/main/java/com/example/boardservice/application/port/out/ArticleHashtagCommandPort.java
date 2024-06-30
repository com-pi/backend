package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.ArticleHashtag;

public interface ArticleHashtagCommandPort {


    void save(ArticleHashtag articleHashtag);

    void deleteAllByArticleId(Long articleId);
}
