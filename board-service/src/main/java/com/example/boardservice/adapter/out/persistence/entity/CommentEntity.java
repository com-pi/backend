package com.example.boardservice.adapter.out.persistence.entity;

import com.example.boardservice.domain.Comment;
import com.example.common.baseentity.DeletedAtAbstractEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comments")
public class CommentEntity extends DeletedAtAbstractEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String content;

    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CommentEntity parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST)
    private List<CommentEntity> children = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private CommonArticleEntity article;

    private LocalDate createdDate;

    @Builder
    public CommentEntity(Long commentId, String content, Long memberId, CommentEntity parent, List<CommentEntity> children, CommonArticleEntity article, LocalDate createdDate) {
        this.commentId = commentId;
        this.content = content;
        this.memberId = memberId;
        this.parent = parent;
        this.children = children;
        this.article = article;
        this.createdDate = createdDate;
    }

    /**
     * 연관관계
     */
    public void addParent(CommentEntity parentEntity) {
        this.parent = parentEntity;
        parentEntity.getChildren().add(this);
    }

    public void addArticle(Long articleId) {
        this.article = GeneralArticleEntity.ofId(articleId);
    }

    /**
     *
     */
    public static CommentEntity fromDomain(Comment comment) {
        CommentEntity.CommentEntityBuilder commentBuilder = CommentEntity.builder()
                .commentId(comment.getCommentId())
                .memberId(comment.getMemberId())
                .article(CommonArticleEntity.ofId(comment.getArticleId()))
                .content(comment.getContent())
                .createdDate(comment.getCreatedDate());

        Optional.ofNullable(comment.getParent())
                .map(parent -> CommentEntity.ofId(parent.getCommentId()))
                .ifPresent(commentBuilder::parent);

        Optional.ofNullable(comment.getChildren())
                .map(children -> children.stream()
                        .map(child -> CommentEntity.ofId(child.getCommentId()))
                        .collect(Collectors.toList()))
                .ifPresent(commentBuilder::children);

        return commentBuilder.build();
    }

    public Comment toDomain() {
        Comment.CommentBuilder commentBuilder = Comment.builder()
                .commentId(this.commentId)
                .memberId(this.memberId)
                .articleId(this.article.getArticleId())
                .content(this.content)
                .createdDate(this.createdDate);

        Optional.ofNullable(this.getParent())
                .map(parent -> Comment.ofId(parent.getCommentId()))
                .ifPresent(commentBuilder::parent);

        Optional.ofNullable(this.getChildren())
                .map(children -> children.stream()
                        .map(child -> Comment.ofId(child.getCommentId()))
                        .collect(Collectors.toList()))
                .ifPresent(commentBuilder::children);

        return commentBuilder.build();
    }

    public static CommentEntity ofId(Long commentId) {
        return CommentEntity.builder()
                .commentId(commentId)
                .build();
    }

    public void update(Comment comment) {
        this.content = comment.getContent();
    }

    @Override
    public void delete() {
        super.delete();
    }
}
