package com.example.encycloservice.adapter.out.persistence;

import com.example.encycloservice.DomainMapper;
import com.example.encycloservice.application.port.out.EncyclopediaQuery;
import com.example.encycloservice.domain.PlantBrief;
import com.example.encycloservice.domain.PlantSpecies;
import com.example.encycloservice.domain.SearchPlantQueryResult;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EncyclopediaQueryAdapter implements EncyclopediaQuery {

    private final EncyclopediaRepository encyclopediaRepository;

    @Override
    public SearchPlantQueryResult searchByKeyword(String keyword, int page, int size) {
        var queryResult = encyclopediaRepository.searchUsingNamePattern(keyword, PageRequest.of(page, size));
        return SearchPlantQueryResult.builder()
                .totalElement(queryResult.getTotalElements())
                .totalPage(queryResult.getTotalPages())
                .results(queryResult.get().map(DomainMapper::toDomain).toList())
                .build();
    }

    @Nullable
    @Override
    public PlantSpecies getById(Long id) {
        return encyclopediaRepository.findById(id)
                .flatMap(entity -> Optional.ofNullable(entity.toDomain()))
                .orElse(null);
    }

    @Nullable
    @Override
    @Cacheable(cacheManager = "redisCacheManager", cacheNames = "plant_brief", key = "#id")
    public PlantBrief getBriefById(Long id) {
        return encyclopediaRepository.findById(id)
                .flatMap(entity -> Optional.ofNullable(entity.toBrief()))
                .orElse(null);
    }

}
