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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long articleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    protected MemberEntity member;

    protected String title;

    protected String content;

    protected Integer viewCount;

    @Convert(converter = JsonToStringListConverter.class)
    protected List<String> imageUrls;

    @Transient
    protected ArticleType articleType;


    @PostLoad
    public void setArticleTypeAfterLoad() {
        this.articleType = ArticleType.fromString(this.getClass().getAnnotation(DiscriminatorValue.class).value());
    }

    public Article toArticle() {
        return Article.builder()
                .articleId(this.articleId)
                .memberId(this.member.getMemberId())
                .title(this.title)
                .content(this.content)
                .viewCount(this.viewCount)
                .imageUrls(this.imageUrls)
                .articleType(this.articleType)
                .build();
    }

    public static CommonArticleEntity from(Long articleId) {
        return new CommonArticleEntity(articleId);
    }

    public CommonArticleEntity(Long articleId) {
        this.articleId = articleId;
    }


}
