package com.example.boardservice.adapter.in.web.response;

import com.example.boardservice.domain.GeneralArticle;

import java.time.LocalDateTime;
import java.util.List;

public record GeneralArticleResponse(
        Long articleId,
//        MemberResponse member,
        LocalDateTime createdAt,
        String title,
        String content,
        List<String> hashtagList,
        List<String> imageUrls
        // @TODO 좋아요 개수, 댓글 개수
) {
    public static GeneralArticleResponse from(GeneralArticle generalArticle) {
        return new GeneralArticleResponse(
                generalArticle.getArticleId(),
//                MemberResponse.from(member),
                generalArticle.getCreatedAt(),
                generalArticle.getTitle(),
                generalArticle.getContent(),
                generalArticle.getHashtagList(),
                generalArticle.getImageUrls()
        );
    }
}
