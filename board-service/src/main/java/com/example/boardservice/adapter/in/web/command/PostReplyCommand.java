package com.example.boardservice.adapter.in.web.command;

import com.example.boardservice.adapter.in.web.request.PostReplyRequest;
import com.example.boardservice.domain.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class PostReplyCommand {

    private Long memberId;
    private final String content;
    private final Long parentId;

    public static PostReplyCommand of(PostReplyRequest request, Long memberId) {
        return PostReplyCommand.builder()
                .memberId(memberId)
                .content(request.getContent())
                .parentId(request.getParentId())
                .build();
    }

    public Comment toDomain() {
        return Comment.builder()
                .memberId(memberId)
                .content(content)
                .parent(Comment.ofId(parentId))
                .createdDate(LocalDate.now())
                .likeCount(0)
                .build();
    }
}
