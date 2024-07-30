package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.ArticleLike;

public interface ArticleLikeCommandPort {

    ArticleLike save(ArticleLike like);
}
