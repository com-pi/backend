package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.Like;

public interface LikeCommandPort {

    Like save(Like like);
}
