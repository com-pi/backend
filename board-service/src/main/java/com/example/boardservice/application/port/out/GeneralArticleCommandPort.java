package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.GeneralArticle;

public interface GeneralArticleCommandPort {

    GeneralArticle save(GeneralArticle article);

    void update(GeneralArticle article);

    void delete(GeneralArticle article);
}
