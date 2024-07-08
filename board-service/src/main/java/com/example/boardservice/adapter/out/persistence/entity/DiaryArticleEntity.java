package com.example.boardservice.adapter.out.persistence.entity;

import com.example.boardservice.domain.ArticleType;
import com.example.boardservice.domain.DiaryArticle;
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
@DiscriminatorValue("general_board")
@Table(name = "diary_board")
public class DiaryArticleEntity extends CommonArticleEntity {

    @Builder
    public DiaryArticleEntity(Long articleId, Long memberId, String title, String content, Integer viewCount, List<String> imageUrls, ArticleType articleType) {
        super(articleId, memberId, title, content, viewCount, imageUrls, articleType);
    }

    public DiaryArticle toDomain() {
        return DiaryArticle.builder()
                .articleId(this.articleId)
                .memberId(this.memberId)
                .title(this.title)
                .content(this.content)
                .viewCount(this.viewCount)
                .imageUrls(this.imageUrls)
                .createdAt(this.getCreatedAt())
                .build();
    }

    public static DiaryArticleEntity fromDomain(DiaryArticle article) {
        return DiaryArticleEntity.builder()
                .articleId(article.getArticleId())
                .memberId(article.getMemberId())
                .title(article.getTitle())
                .content(article.getContent())
                .viewCount(article.getViewCount())
                .imageUrls(article.getImageUrls())
                .build();
    }

}
