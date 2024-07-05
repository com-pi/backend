package com.example.boardservice.adapter.in.web.response;

import com.example.boardservice.domain.QnaArticle;

import java.util.List;

public record QnaArticleListResponse(
        List<QnaArticleResponse> qnaArticleResponseList
) {
    public static QnaArticleListResponse from(List<QnaArticle> articleList) {
        List<QnaArticleResponse> response = articleList.stream()
                .map(QnaArticleResponse::from)
                .toList();
        return new QnaArticleListResponse(response);
    }
}
