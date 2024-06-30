package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.HashtagEntity;
import com.example.boardservice.application.port.out.HashtagQueryPort;
import com.example.boardservice.domain.Hashtag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HashtagQueryAdapter implements HashtagQueryPort {

    private final HashtagRepository hashtagRepository;

    @Override
    @Nullable
    public Hashtag findHashtagByName(String name) {
        Optional<HashtagEntity> hashtagEntity = hashtagRepository.findByName(name);
        return hashtagEntity.map(HashtagEntity::toDomain).orElse(null);
    }
}
