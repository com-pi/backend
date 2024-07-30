package com.example.boardservice.adapter.out.persistence.entity;


import com.example.boardservice.domain.ArticleLike;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(name = "article_likes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"article_id", "member_id"})
})
public class ArticleLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private CommonArticleEntity article;

    private Long memberId;

    private Boolean isLiked;


    public static ArticleLikeEntity from(ArticleLike like) {
        return ArticleLikeEntity.builder()
                .likeId(like.getLikeId())
                .article(CommonArticleEntity.ofId(like.getArticleId()))
                .memberId(like.getMemberId())
                .isLiked(like.getIsLiked())
                .build();
    }

    public ArticleLike toDomain() {
        return ArticleLike.builder()
                .likeId(this.likeId)
                .articleId(this.article.getArticleId())
                .memberId(this.memberId)
                .isLiked(this.isLiked)
                .build();
    }
}