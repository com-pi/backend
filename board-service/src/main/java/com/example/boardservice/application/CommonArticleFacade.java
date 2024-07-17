package com.example.boardservice.application;

import com.example.boardservice.application.port.in.CommonArticleUseCase;
import com.example.boardservice.domain.Article;
import com.example.boardservice.domain.ArticleHashtag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CommonArticleFacade implements CommonArticleUseCase {

    private final CommonArticleService articleService;
    private final ArticleHashtagService articleHashtagService;
    private final LikeService likeService;
    private final CommentService commentService;

    public List<Article> getArticleList(String type, Pageable pageable) {
        Page<Article> articlePage = articleService.getArticleList(type, pageable);
        return articlePage
                .stream()
                .peek(article -> {
                    List<ArticleHashtag> articleHashtags = articleHashtagService.getHashtagListByArticle(article.getArticleId());
                    List<String> hashtagList = articleHashtags.stream()
                            .map(articleHashtag -> articleHashtag.getHashtag().getName())
                            .toList();
                    article.addHashtagList(hashtagList);
                })
                .toList();
    }

    @Override
    public List<Article> getArticleListByHashtag(String name, Pageable pageable) {
        Long hashtagId = articleHashtagService.getArticleIdByHashtagName(name);

        if(Objects.isNull(hashtagId)) {
            return Collections.emptyList();
        }

        List<Long> articleIdList = articleHashtagService.getArticleIdByHashtagId(hashtagId, pageable).stream()
                .map(ArticleHashtag::getArticleId)
                .toList();

        List<Article> articleList = articleService.getArticleListByArticleId(articleIdList, pageable);

        articleList.forEach(article -> {
            List<String> hashtagList = articleHashtagService.getHashtagListByArticle(article.getArticleId()).stream()
                    .map(articleHashtag -> articleHashtag.getHashtag().getName())
                    .toList();
            article.addHashtagList(hashtagList);
        });
        return articleList;
    }

    public Article getArticle(Long articleId) {
        Article article = this.articleService.getArticle(articleId);
        // 해시태그
        List<String> hashtagList = articleHashtagService.getHashtagListByArticle(articleId)
                .stream()
                .map(articleHashtag -> articleHashtag.getHashtag().getName())
                .toList();
        article.addHashtagList(hashtagList);

        return article;
    }
}
