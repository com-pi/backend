package com.example.boardservice.application;

import com.example.boardservice.application.port.out.CommonArticleQueryPort;
import com.example.boardservice.domain.Article;
import com.example.boardservice.domain.ArticleType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonArticleService {

    private final CommonArticleQueryPort articleQueryPort;

    public Page<Article> getArticleList(String type, Pageable pageable) {
        ArticleType articleType = ArticleType.from(type);
        return articleType == ArticleType.COMMON_ARTICLE ?
                articleQueryPort.getArticleList(pageable)
                : articleQueryPort.getArticleList(articleType, pageable);
    }

    public List<Article> getArticleListByArticleId(List<Long> articleIdList, Pageable pageable) {
        return articleQueryPort.getArticleListByArticleId(articleIdList, pageable);
    }

    public Article getArticle(Long articleId) {
        return articleQueryPort.getArticle(articleId);
    }

    public Page<Article> searchArticleList(String keyword, String type, Pageable pageable) {
        ArticleType articleType = ArticleType.from(type);
        return articleType == ArticleType.COMMON_ARTICLE ?
                articleQueryPort.searchArticleList(keyword, pageable)
                : articleQueryPort.searchArticleList(keyword, articleType, pageable);
    }

    public Page<Article> getArticleListByMemberId(Long memberId, Pageable pageable) {
        return articleQueryPort.getArticleListByMemberId(memberId, pageable);
    }
}
