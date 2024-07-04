package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.ArticleHashtag;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleHashtagQueryPort {
    List<ArticleHashtag> getArticleHashtagListByArticleId(Long articleId);

    List<ArticleHashtag> getArticleIdByHashtagId(Long hashtagId, Pageable pageable);
}
