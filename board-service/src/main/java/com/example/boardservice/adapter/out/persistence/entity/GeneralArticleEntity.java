package com.example.boardservice.adapter.out.persistence.entity;

import com.example.boardservice.domain.ArticleType;
import com.example.boardservice.domain.GeneralArticle;
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
@DiscriminatorValue("GENERAL_BOARD")
@Table(name = "GENERAL_BOARD")
public class GeneralArticleEntity extends CommonArticleEntity {

    @Builder
    public GeneralArticleEntity(Long articleId, Long memberId, String title, String content, Integer viewCount, Integer likeCount, Integer commentCount, List<String> imageUrls, ArticleType articleType) {
        super(articleId, memberId, title, content, viewCount, likeCount, commentCount, imageUrls, articleType);
    }

    public GeneralArticle toDomain() {
        return GeneralArticle.builder()
                .articleId(this.articleId)
                .memberId(this.memberId)
                .title(this.title)
                .content(this.content)
                .viewCount(this.viewCount)
                .imageUrls(this.imageUrls)
                .createdAt(this.getCreatedAt())
                .likeCount(this.likeCount)
                .commentCount(this.commentCount)
                .build();
    }

    public static GeneralArticleEntity fromDomain(GeneralArticle generalArticle) {
        return GeneralArticleEntity.builder()
                .articleId(generalArticle.getArticleId())
                .memberId(generalArticle.getMemberId())
                .title(generalArticle.getTitle())
                .content(generalArticle.getContent())
                .viewCount(generalArticle.getViewCount())
                .imageUrls(generalArticle.getImageUrls())
                .likeCount(generalArticle.getLikeCount())
                .commentCount(generalArticle.getCommentCount())
                .build();
    }

    public void update(GeneralArticle article) {
        this.title = article.getTitle();
        this.content = article.getContent();
        this.imageUrls = article.getImageUrls();
    }
}
