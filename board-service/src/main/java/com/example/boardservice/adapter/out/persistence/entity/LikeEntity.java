package com.example.boardservice.adapter.out.persistence.entity;


import com.example.boardservice.domain.Like;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(name = "likes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"article_id", "member_id"})
})
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private CommonArticleEntity article;

    private Long memberId;

    private Boolean isLiked;


    public static LikeEntity from(Like like) {
        return LikeEntity.builder()
                .likeId(like.getLikeId())
                .article(CommonArticleEntity.ofId(like.getArticleId()))
                .memberId(like.getMemberId())
                .isLiked(like.getIsLiked())
                .build();
    }

    public Like toDomain() {
        return Like.builder()
                .likeId(this.likeId)
                .articleId(this.article.getArticleId())
                .memberId(this.memberId)
                .isLiked(this.isLiked)
                .build();
    }
}