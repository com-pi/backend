package com.example.boardservice.adapter.in.web.command;

import com.example.boardservice.domain.Like;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UnlikeArticleCommand {
    private Long likeId;
    private Long memberId;

    public static UnlikeArticleCommand of(Long likeId, Long memberId) {
        return UnlikeArticleCommand.builder()
                .likeId(likeId)
                .memberId(memberId)
                .build();
    }

    public Like toDomain() {
        return Like.builder()
                .likeId(likeId)
                .memberId(memberId)
                .build();
    }
}