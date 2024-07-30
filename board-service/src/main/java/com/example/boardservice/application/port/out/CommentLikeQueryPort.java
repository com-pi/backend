package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.CommentLike;

import java.util.Optional;

public interface CommentLikeQueryPort {

    Optional<CommentLike> findLike(Long commentId, Long memberId);

    CommentLike getLike(Long commentId, Long memberId);
}
