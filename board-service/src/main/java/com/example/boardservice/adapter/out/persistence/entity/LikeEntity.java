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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    private Boolean isLiked;


    public static LikeEntity from(Like like) {
        return LikeEntity.builder()
                .likeId(like.getLikeId())
                .article(CommonArticleEntity.from(like.getArticleId()))
                .member(MemberEntity.from(like.getMemberId()))
                .isLiked(like.getIsLiked())
                .build();
    }

    public Like toDomain() {
        return Like.builder()
                .likeId(this.likeId)
                .articleId(this.article.getArticleId())
                .memberId(this.member.getMemberId())
                .isLiked(this.isLiked)
                .build();
    }
}