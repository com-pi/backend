package com.example.boardservice.application;

import com.example.boardservice.adapter.in.web.response.CommonArticleResponse;
import com.example.boardservice.application.port.in.CommonArticleUseCase;
import com.example.boardservice.domain.Article;
import com.example.boardservice.domain.ArticleHashtag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public CommonArticleResponse getArticleListByHashtag(String name, Pageable pageable) {
        Page<Article> articlePage = commonArticleService.getArticleListByHashtag(name, pageable);

        return null;
    }
}
