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

    private Long memberId;

    private Comment parent;

    private List<Comment> children;

    private Long articleId;

    private LocalDate createdDate;

    private Boolean isEditable;

    private Integer likeCount;

    public static Comment ofId(Long commentId) {
        return Comment.builder()
                .commentId(commentId)
                .build();
    }

    public void addEditable(Long currentMemberId) {
        this.isEditable = Objects.equals(this.memberId, currentMemberId);
    }

}
