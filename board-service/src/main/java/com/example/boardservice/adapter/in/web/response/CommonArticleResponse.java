package com.example.boardservice.adapter.in.web.response;

import com.example.boardservice.domain.Article;

import java.time.LocalDateTime;
import java.util.List;

public record CommonArticleResponse(
        Long articleId,
//        MemberResponse member,
        LocalDateTime createdAt,
        String title,
        String content,
        List<String> hashtagList,
        List<String> imageUrls
        // @TODO 좋아요 개수, 댓글 개수
) {
    public static CommonArticleResponse from(Article article) {
        return new CommonArticleResponse(
                article.getArticleId(),
//                MemberResponse.from(member),
                article.getCreatedAt(),
                article.getTitle(),
                article.getContent(),
                article.getHashtagList(),
                article.getImageUrls()
        );
    }
}