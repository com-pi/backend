package com.example.boardservice.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Builder
public class Comment {

    private Long commentId;

    private String content;

    private Member member;

    private Comment parent;

    private List<Comment> children;

    private Long articleId;

    private LocalDate createdDate;

    private Boolean isEditable;

    private Integer likeCount;

    private Boolean isLiked;

    public static Comment ofId(Long commentId) {
        return Comment.builder()
                .commentId(commentId)
                .build();
    }

    public void addEditable(Long currentMemberId) {
        this.isEditable = Objects.equals(this.member.getMemberId(), currentMemberId);
    }

    public void addLikeStatus(boolean isLiked) {
        this.isLiked = isLiked;
    }

    public Long getAuthorId() {
        return this.member.getMemberId();
    }

    public void addMember(Member member) {
        this.member = member;
    }

}
