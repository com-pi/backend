package com.example.boardservice.adapter.out.persistence.entity;

import com.example.boardservice.adapter.out.persistence.converter.JsonToStringListConverter;
import com.example.boardservice.domain.Article;
import com.example.boardservice.domain.ArticleType;
import com.example.common.baseentity.DeletedAtAbstractEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "article_type")
@Table(name = "article")
public class CommonArticleEntity extends DeletedAtAbstractEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long articleId;

    protected Long memberId;

    protected String title;

    protected String content;

    protected Integer viewCount;

    protected Integer likeCount;

    protected Integer commentCount;

    @Convert(converter = JsonToStringListConverter.class)
    protected List<String> imageUrls;

    @Enumerated(EnumType.STRING)
    protected ArticleType type;

    @PostLoad
    public void setArticleTypeAfterLoad() {
        this.type = ArticleType.from(this.getClass().getAnnotation(DiscriminatorValue.class).value());
    }

    public Article toArticle() {
        return Article.builder()
                .articleId(this.articleId)
                .memberId(this.memberId)
                .title(this.title)
                .content(this.content)
                .viewCount(this.viewCount)
                .imageUrls(this.imageUrls)
                .type(this.type)
                .createdAt(this.getCreatedAt())
                .likeCount(this.likeCount)
                .commentCount(this.commentCount)
                .build();
    }

    public static CommonArticleEntity ofId(Long articleId) {
        return new CommonArticleEntity(articleId);
    }

    public CommonArticleEntity(Long articleId) {
        this.articleId = articleId;
    }

    @Override
    public void delete() {
        super.delete();
    }

    public void increaseLikeCount() {
        this.likeCount += 1;
    }

    public void decreaseLikeCount() {
        this.likeCount -= 1;
    }

    public void increaseCommentCount() {
        this.commentCount += 1;
    }

    public  void decreaseCommentCount() {
        this.commentCount -= 1;
    }

}
