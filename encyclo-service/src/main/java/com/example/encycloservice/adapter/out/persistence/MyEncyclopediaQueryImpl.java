package com.example.encycloservice.adapter.out.persistence;

import com.example.encycloservice.adapter.out.persistence.entity.EncyclopediaPlantEntity;
import com.example.encycloservice.adapter.out.persistence.entity.MyEncyclopediaEntity;
import com.example.encycloservice.adapter.out.persistence.repository.MyEncyclopediaRepository;
import com.example.encycloservice.application.port.out.MyEncyclopediaQuery;
import com.example.encycloservice.domain.MyEncyclopedia;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MyEncyclopediaQueryImpl implements MyEncyclopediaQuery {

    private final MyEncyclopediaRepository myEncyclopediaRepository;

    public List<MyEncyclopedia> getMyEncyclopediaList(Long memberId) {
        List<MyEncyclopediaEntity> entityList = myEncyclopediaRepository.findListByMemberId(memberId);
        return entityList.stream()
                .sorted(Comparator.comparing(MyEncyclopediaEntity::getUpdatedAt).reversed())
                .map(MyEncyclopediaEntity::toDomain)
                .toList();
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

    @Override
    public Page<EncyclopediaPlantEntity> getPlantListByEncyclopediaId(Long myEncyclopediaId, Integer page, Integer size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedAt"));
        return myEncyclopediaRepository.findPlantListByEncyclopediaId(myEncyclopediaId, pageable);
    }

}
