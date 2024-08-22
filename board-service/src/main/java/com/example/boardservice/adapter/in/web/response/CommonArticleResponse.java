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
        Boolean isLiked,
        List<String> hashtagList,
        List<String> imageUrls,
        Boolean isEditable
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
                article.getIsLiked(),
                article.getHashtagList(),
                article.getImageUrls(),
                article.getIsEditable()
        );
    }
}