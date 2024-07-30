package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.CommentLike;

public interface CommentLikeCommandPort {
    CommentLike save(CommentLike like);
}
