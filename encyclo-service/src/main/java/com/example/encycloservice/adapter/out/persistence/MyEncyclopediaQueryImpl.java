package com.example.encycloservice.adapter.out.persistence;

import com.example.encycloservice.adapter.out.persistence.entity.MyEncyclopediaEntity;
import com.example.encycloservice.adapter.out.persistence.repository.MyEncyclopediaRepository;
import com.example.encycloservice.application.port.out.MyEncyclopediaQuery;
import com.example.encycloservice.domain.MyEncyclopedia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MyEncyclopediaQueryImpl implements MyEncyclopediaQuery {

    private final MyEncyclopediaRepository myEncyclopediaRepository;

    @Override
    public List<MyEncyclopedia> getMyEncyclopediaList(Long memberId) {
        List<MyEncyclopediaEntity> entityList = myEncyclopediaRepository.findListByMemberId(memberId);
        return entityList.stream().map(MyEncyclopediaEntity::toDomain).toList();
    }

    @Override
    public Optional<MyEncyclopedia> findById(Long id) {
        return myEncyclopediaRepository.findById(id).map(MyEncyclopediaEntity::toDomain);
    }

    @Override
    public Optional<MyEncyclopedia> findEncyclopediaWithContentById(Long myEncyclopediaId) {
        return myEncyclopediaRepository.findEncyclopediaById(myEncyclopediaId)
                .map(MyEncyclopediaEntity::toDomain);
    }

    @Override
    public Optional<MyEncyclopedia> findEncyclopedia(Long fromEncyclopediaId) {
        return myEncyclopediaRepository.findEncyclopediaById(fromEncyclopediaId)
                .map(MyEncyclopediaEntity::toDomain);
    }
}
