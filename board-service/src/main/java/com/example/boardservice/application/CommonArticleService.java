package com.example.boardservice.application;

import com.example.boardservice.application.port.out.CommonArticleQueryPort;
import com.example.boardservice.domain.Article;
import com.example.boardservice.domain.ArticleType;
import com.example.boardservice.security.PassportHolder;
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
        Page<Article> articlePage =
                articleType == ArticleType.COMMON_ARTICLE ?
                articleQueryPort.getArticleList(pageable)
                : articleQueryPort.getArticleList(articleType, pageable);
        checkEditable(articlePage.getContent());
        return articlePage;
    }

    public List<Article> getArticleListByArticleId(List<Long> articleIdList, Pageable pageable) {
        List<Article> articleList = articleQueryPort.getArticleListByArticleId(articleIdList, pageable);
        checkEditable(articleList);
        return articleList;
    }

    public Article getArticle(Long articleId) {
        Article article = articleQueryPort.getArticle(articleId);
        checkEditable(article);
        return article;
    }

    public Page<Article> searchArticleList(String keyword, String type, Pageable pageable) {
        ArticleType articleType = ArticleType.from(type);
        Page<Article> articlePage =
                articleType == ArticleType.COMMON_ARTICLE ?
                articleQueryPort.searchArticleList(keyword, pageable)
                : articleQueryPort.searchArticleList(keyword, articleType, pageable);
        checkEditable(articlePage.getContent());
        return articlePage;
    }

    public Page<Article> getArticleListByMemberId(Long memberId, Pageable pageable) {
        Page<Article> articlePage = articleQueryPort.getArticleListByMemberId(memberId, pageable);
        checkEditable(articlePage.getContent());
        return articlePage;
    }

    public Page<Article> getLikedArticleId(Long memberId, Pageable pageable) {
        Page<Article> articlePage = articleQueryPort.getLikedArticleId(memberId, pageable);
        checkEditable(articlePage.getContent());
        return articlePage;
    }

    /**
     * private
     */
    private void checkEditable(List<Article> articleList)  {
        articleList.forEach(article -> article.addEditable(PassportHolder.getPassport().memberId()));
    }

    private void checkEditable(Article article)  {
        article.addEditable(PassportHolder.getPassport().memberId());
    }
}
