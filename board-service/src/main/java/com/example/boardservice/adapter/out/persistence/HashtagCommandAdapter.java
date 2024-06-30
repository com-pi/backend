package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.HashtagEntity;
import com.example.boardservice.application.port.out.HashtagCommandPort;
import com.example.boardservice.domain.Hashtag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HashtagCommandAdapter implements HashtagCommandPort {

    private final HashtagRepository hashtagRepository;

    @Override
    public Hashtag save(Hashtag hashtag) {
        HashtagEntity hashtagEntity = hashtagRepository.save(HashtagEntity.from(hashtag));
        return hashtagEntity.toDomain();
    }
}
