package com.example.boardservice.application.port.in;

import com.example.boardservice.adapter.in.web.command.GetArticleListCommand;
import com.example.boardservice.domain.Article;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommonArticleUseCase {

    List<Article> getArticleList(GetArticleListCommand command);

    Article getArticle(Article article);

    List<Article> getArticleListByHashtag(String name, Pageable pageable);
}
