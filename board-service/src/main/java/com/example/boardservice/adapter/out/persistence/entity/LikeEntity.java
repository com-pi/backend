package com.example.boardservice.adapter.out.persistence.entity;


import com.example.boardservice.domain.ArticleType;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    private Boolean isLiked;

    @Enumerated(EnumType.STRING)
    private ArticleType articleType;

    public static LikeEntity from(Like like) {
        CommonArticleEntity article = null;
        
        ArticleType articleType = like.getArticleType();
        if(articleType == ArticleType.BUY_AND_SELL) {
            article = BuyAndSellEntity.from(like.getArticleId());
        }
        return LikeEntity.builder()
                .likeId(like.getLikeId())
                .article(article)
                .member(MemberEntity.from(like.getMemberId()))
                .isLiked(like.getIsLiked())
                .articleType(articleType)
                .build();
    }

    public Like toDomain() {
        return Like.builder()
                .likeId(this.likeId)
                .articleId(this.article.getArticleId())
                .memberId(this.member.getMemberId())
                .isLiked(this.isLiked)
                .articleType(this.articleType)
                .build();
    }
}