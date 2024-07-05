package com.example.boardservice.adapter.in.web.response;

import com.example.boardservice.domain.QnaArticle;

import java.time.LocalDateTime;
import java.util.List;

public record QnaArticleResponse(
        Long articleId,
//        MemberResponse member,
        LocalDateTime createdAt,
        String title,
        String content,
        List<String> hashtagList,
        List<String> imageUrls
        // @TODO 좋아요 개수, 댓글 개수
) {
    public static QnaArticleResponse from(QnaArticle article) {
        return new QnaArticleResponse(
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
