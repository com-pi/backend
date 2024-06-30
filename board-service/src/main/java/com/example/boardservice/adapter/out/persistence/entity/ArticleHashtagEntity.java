package com.example.boardservice.adapter.out.persistence.entity;

import com.example.boardservice.domain.ArticleHashtag;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(name = "article_hashtag")
public class ArticleHashtagEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleHashtagId;

    private Long articleId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id")
    private HashtagEntity hashtag;

    /**
     * 연관관계
     */
    public static ArticleHashtagEntity of(CommonArticleEntity articleEntity, HashtagEntity hashtagEntity) {
        return ArticleHashtagEntity.builder()
                .articleId(articleEntity.getArticleId())
                .hashtag(hashtagEntity)
                .build();
    }

    /**
     * 생성자
     */
    public ArticleHashtagEntity(Long articleHashtagId) {
        this.articleHashtagId = articleHashtagId;
    }

    /**
     *
     */
    public ArticleHashtag toDomain() {
        return ArticleHashtag.builder()
                .articleHashtagId(articleHashtagId)
                .articleId(articleId)
                .hashtag(hashtag.toDomain())
                .build();
    }

    public static ArticleHashtagEntity fromDomain(ArticleHashtag articleHashtag) {
        return ArticleHashtagEntity.builder()
                .articleHashtagId(articleHashtag.getArticleHashtagId())
                .articleId(articleHashtag.getArticleId())
                .hashtag(HashtagEntity.from(articleHashtag.getHashtag()))
                .build();
    }

    public static ArticleHashtagEntity ofId(Long articleHashtagId) {
        return new ArticleHashtagEntity(articleHashtagId);
    }

}
