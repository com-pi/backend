package com.example.boardservice.application.port.in;

import com.example.boardservice.domain.Article;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommonArticleUseCase {

    List<Article> getCommonArticleList(Pageable pageable);

    List<Article> getArticleListByHashtag(String name, Pageable pageable);

}
