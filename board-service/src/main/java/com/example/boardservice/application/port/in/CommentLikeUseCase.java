package com.example.boardservice.application.port.in;

import com.example.boardservice.domain.CommentLike;

public interface CommentLikeUseCase {
    Long like(CommentLike like);

    Long unlike(CommentLike like);
}
