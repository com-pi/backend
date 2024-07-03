package com.example.boardservice.application.port.in;

import com.example.boardservice.adapter.in.web.response.CommonArticleResponse;
import com.example.boardservice.domain.Article;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommonArticleUseCase {

    List<Article> getCommonArticleList(Pageable pageable);

    CommonArticleResponse getArticleListByHashtag(String name, Pageable pageable);

}
