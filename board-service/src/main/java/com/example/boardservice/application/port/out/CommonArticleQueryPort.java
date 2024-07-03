package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommonArticleQueryPort {

    Page<Article> getArticleList(Pageable pageable);
}
