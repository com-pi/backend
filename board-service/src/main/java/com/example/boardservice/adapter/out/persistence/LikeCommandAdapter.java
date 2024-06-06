package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.LikeEntity;
import com.example.boardservice.application.port.out.LikeCommandPort;
import com.example.boardservice.domain.Like;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeCommandAdapter implements LikeCommandPort {

    private final LikeRepository likeRepository;

    @Override
    public Like save(Like like) {
        LikeEntity likeEntity = likeRepository.save(LikeEntity.from(like));
        return likeEntity.toDomain();
    }
}