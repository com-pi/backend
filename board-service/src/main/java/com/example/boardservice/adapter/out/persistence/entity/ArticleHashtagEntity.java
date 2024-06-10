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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private CommonArticleEntity article;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "hashtag_id")
    private HashtagEntity hashtag;

    /**
     * 연관관계
     */
    public static ArticleHashtagEntity of(CommonArticleEntity articleEntity, HashtagEntity hashtagEntity) {
        return ArticleHashtagEntity.builder()
                .article(articleEntity)
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
                .articleId(article.getArticleId())
                .hashtag(hashtag.toDomain())
                .build();
    }

    public static ArticleHashtagEntity ofId(Long articleHashtagId) {
        return new ArticleHashtagEntity(articleHashtagId);
    }

}
