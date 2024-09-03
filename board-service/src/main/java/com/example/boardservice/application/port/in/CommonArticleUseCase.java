package com.example.boardservice.application.port.in;

import com.example.boardservice.adapter.in.web.command.GetArticleListCommand;
import com.example.boardservice.adapter.in.web.command.GetSearchedArticleListCommand;
import com.example.boardservice.domain.Article;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommonArticleUseCase {

    List<Article> getArticleList(GetArticleListCommand command);

    Article getArticle(Article article);

    List<Article> getArticleListByHashtag(String name, String type, Pageable pageable);

    List<Article> searchArticleList(GetSearchedArticleListCommand command);

    List<Article> getArticleListByMember(Long memberId, Pageable pageable);

    List<Article> getLikedArticleListByMember(Long memberId, Pageable pageable);
}
