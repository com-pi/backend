package com.example.boardservice.adapter.in.web.response;

import com.example.boardservice.domain.Article;

import java.util.List;

public record CommonArticleListResponse(
        List<CommonArticleResponse> commonArticleResponseList
) {
    public static CommonArticleListResponse from(List<Article> articleList) {
        List<CommonArticleResponse> response = articleList.stream()
                .map(CommonArticleResponse::from)
                .toList();
        return new CommonArticleListResponse(response);
    }
}
