package com.example.boardservice.adapter.out.persistence.entity;

import com.example.boardservice.domain.CommentLike;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(name = "comment_likes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"comment_id", "member_id"})
})
public class CommentLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private CommentEntity comment;

    private Long memberId;

    private Boolean isLiked;

    public static CommentLikeEntity from(CommentLike like) {
        return CommentLikeEntity.builder()
                .likeId(like.getLikeId())
                .comment(CommentEntity.ofId(like.getCommentId()))
                .memberId(like.getMemberId())
                .isLiked(like.getIsLiked())
                .build();
    }

    public CommentLike toDomain() {
        return CommentLike.builder()
                .likeId(this.likeId)
                .commentId(this.comment.getCommentId())
                .memberId(this.memberId)
                .isLiked(this.isLiked)
                .build();
    }
}
