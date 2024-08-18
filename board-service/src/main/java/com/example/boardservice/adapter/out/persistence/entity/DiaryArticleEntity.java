package com.example.boardservice.adapter.out.persistence.entity;

import com.example.boardservice.domain.ArticleType;
import com.example.boardservice.domain.DiaryArticle;
import com.example.boardservice.domain.Member;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@DiscriminatorValue("DIARY_BOARD")
@Table(name = "DIARY_BOARD")
public class DiaryArticleEntity extends CommonArticleEntity {

    @Builder
    public DiaryArticleEntity(Long articleId, Long memberId, String title, String content, Integer viewCount, Integer likeCount, Integer commentCount, List<String> imageUrls, ArticleType type) {
        super(articleId, memberId, title, content, viewCount, likeCount, commentCount, imageUrls, type);
    }

    public DiaryArticle toDomain() {
        return DiaryArticle.builder()
                .articleId(this.articleId)
                .member(Member.ofId(this.memberId))
                .title(this.title)
                .content(this.content)
                .viewCount(this.viewCount)
                .imageUrls(this.imageUrls)
                .createdAt(this.getCreatedAt())
                .likeCount(this.likeCount)
                .commentCount(this.commentCount)
                .type(this.type)
                .build();
    }

    public static DiaryArticleEntity fromDomain(DiaryArticle article) {
        return DiaryArticleEntity.builder()
                .articleId(article.getArticleId())
                .memberId(article.getMember().getMemberId())
                .title(article.getTitle())
                .content(article.getContent())
                .viewCount(article.getViewCount())
                .imageUrls(article.getImageUrls())
                .likeCount(article.getLikeCount())
                .commentCount(article.getCommentCount())
                .type(article.getType())
                .build();
    }

}
