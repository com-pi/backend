package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.CommentLikeEntity;
import com.example.boardservice.application.port.out.CommentLikeCommandPort;
import com.example.boardservice.domain.CommentLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentLikeCommandAdapter implements CommentLikeCommandPort {

    private final CommentLikeRepository commentLikeRepository;

    @Override
    public CommentLike save(CommentLike like) {
        return commentLikeRepository.save(CommentLikeEntity.from(like)).toDomain();
    }
}
