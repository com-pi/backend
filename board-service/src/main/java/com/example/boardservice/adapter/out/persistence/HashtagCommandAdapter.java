package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.HashtagEntity;
import com.example.boardservice.application.port.out.HashtagCommandPort;
import com.example.boardservice.domain.Hashtag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HashtagCommandAdapter implements HashtagCommandPort {

    private final HashtagRepository hashtagRepository;

    @Override
    public void save(List<Hashtag> hashtagList) {
        List<HashtagEntity> hashtagEntityList = hashtagList.stream().map(HashtagEntity::from).toList();
        hashtagRepository.saveAll(hashtagEntityList);
    }
}
