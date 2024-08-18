package com.example.boardservice.adapter.in.web.response;

import com.example.boardservice.domain.Article;

import java.time.LocalDateTime;
import java.util.List;

public record CommonArticleResponse(
        Long articleId,
        MemberResponse member,
        LocalDateTime createdAt,
        String title,
        String content,
        int likeCount,
        int commentCount,
        boolean isLiked,
        List<String> hashtagList,
        List<String> imageUrls
) {
    public static CommonArticleResponse from(Article article) {
        return new CommonArticleResponse(
                article.getArticleId(),
                MemberResponse.from(article.getMember()),
                article.getCreatedAt(),
                article.getTitle(),
                article.getContent(),
                article.getLikeCount(),
                article.getCommentCount(),
                article.isLiked(),
                article.getHashtagList(),
                article.getImageUrls()
        );
    }
}