package com.example.encycloservice.adapter.out.persistence;

import com.example.encycloservice.DomainMapper;
import com.example.encycloservice.application.port.out.EncyclopediaQuery;
import com.example.encycloservice.domain.PlantSpecies;
import com.example.encycloservice.domain.SearchPlantQueryResult;
import lombok.RequiredArgsConstructor;
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
                .totalElement(queryResult.getTotalPages())
                .totalPage(queryResult.getTotalPages())
                .results(queryResult.get().map(DomainMapper::toDomain).toList())
                .build();
    }

    @Override
    public Optional<PlantSpecies> findById(Long id) {
        return encyclopediaRepository.findById(id)
                .flatMap(entity -> Optional.ofNullable(entity.toDomain()));
    }

}
