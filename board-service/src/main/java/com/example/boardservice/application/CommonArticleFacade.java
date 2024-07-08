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

    private final CommonArticleService commonArticleService;
    private final ArticleHashtagService articleHashtagService;

    @Override
    public List<Article> getCommonArticleList(Pageable pageable) {
        Page<Article> articlePage = commonArticleService.getArticleList(pageable);
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

        List<Article> articleList = commonArticleService.getArticleListByArticleId(articleIdList, pageable);

        articleList.forEach(article -> {
            List<String> hashtagList = articleHashtagService.getHashtagListByArticle(article.getArticleId()).stream()
                    .map(articleHashtag -> articleHashtag.getHashtag().getName())
                    .toList();
            article.addHashtagList(hashtagList);
        });
        return articleList;
    }
}
