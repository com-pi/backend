package com.example.boardservice.application.port.in;

import com.example.boardservice.domain.CommentLike;

import java.util.List;

public interface CommentLikeUseCase {
    Long like(CommentLike like);

    Long unlike(CommentLike like);

    List<Boolean> getLikeStatusByCommentList(List<Long> commentIdList, Long memberId);
}
