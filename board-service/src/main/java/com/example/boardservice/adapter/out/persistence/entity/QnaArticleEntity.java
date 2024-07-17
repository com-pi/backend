package com.example.boardservice.adapter.out.persistence.entity;

import com.example.boardservice.domain.ArticleType;
import com.example.boardservice.domain.QnaArticle;
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
@DiscriminatorValue("QNA_BOARD")
@Table(name = "QNA_BOARD")
public class QnaArticleEntity extends CommonArticleEntity {

    @Builder
    public QnaArticleEntity(Long articleId, Long memberId, String title, String content, Integer viewCount, Integer likeCount, Integer commentCount, List<String> imageUrls, ArticleType articleType) {
        super(articleId, memberId, title, content, viewCount, likeCount, commentCount, imageUrls, articleType);
    }

    public QnaArticle toDomain() {
        return QnaArticle.builder()
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

    public static QnaArticleEntity fromDomain(QnaArticle qnaArticle) {
        return QnaArticleEntity.builder()
                .articleId(qnaArticle.getArticleId())
                .memberId(qnaArticle.getMemberId())
                .title(qnaArticle.getTitle())
                .content(qnaArticle.getContent())
                .viewCount(qnaArticle.getViewCount())
                .imageUrls(qnaArticle.getImageUrls())
                .likeCount(qnaArticle.getLikeCount())
                .commentCount(qnaArticle.getCommentCount())
                .build();
    }

    public void update(QnaArticle article) {
        this.title = article.getTitle();
        this.content = article.getContent();
        this.imageUrls = article.getImageUrls();
    }
    
}
