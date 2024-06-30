package com.example.boardservice.adapter.in.web.response;

import com.example.boardservice.domain.GeneralArticle;

import java.util.List;

public record GeneralArticleListResponse(
        List<GeneralArticleResponse> generalArticleList
) {
    public static GeneralArticleListResponse from(List<GeneralArticle> generalArticleList) {
        List<GeneralArticleResponse> response = generalArticleList.stream()
                .map(GeneralArticleResponse::from)
                .toList();
        return new GeneralArticleListResponse(response);
    }
}
