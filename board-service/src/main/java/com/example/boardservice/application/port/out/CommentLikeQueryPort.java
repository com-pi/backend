package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.CommentLike;

import java.util.List;
import java.util.Optional;

public interface CommentLikeQueryPort {

    Optional<CommentLike> findLike(Long commentId, Long memberId);

    CommentLike getLike(Long commentId, Long memberId);

    List<CommentLike> getLikeByCommentList(List<Long> commentIdList, Long memberId);
}
