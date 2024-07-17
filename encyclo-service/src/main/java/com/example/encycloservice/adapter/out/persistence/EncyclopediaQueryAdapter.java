package com.example.encycloservice.adapter.out.persistence;

import com.example.encycloservice.DomainMapper;
import com.example.encycloservice.adapter.in.response.PlantAddInquiryResponse;
import com.example.encycloservice.adapter.out.persistence.entity.PlantAddInquiryEntity;
import com.example.encycloservice.adapter.out.persistence.entity.PlantSpeciesEntity;
import com.example.encycloservice.application.port.out.EncyclopediaQuery;
import com.example.encycloservice.domain.PlantAddInquiry;
import com.example.encycloservice.domain.PlantBrief;
import com.example.encycloservice.domain.PlantSpecies;
import com.example.encycloservice.domain.SearchPlantQueryResult;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
                .map(PlantSpeciesEntity::toBrief)
                .orElse(new PlantBrief(id, null, null, null));
    }

    @Override
    public PlantAddInquiryResponse searchPlantAddInquiry(Integer page, Integer size, PlantAddInquiry.Status status) {
        Page<PlantAddInquiryEntity> result;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedAt"));
        if(status == null) {
            result = encyclopediaRepository.findAllWithPaging(pageRequest);
        } else {
            result = encyclopediaRepository.findByStatusWithPaging(status, pageRequest);
        }
        return PlantAddInquiryResponse.builder()
                .totalElement(result.getTotalElements())
                .totalPage(result.getTotalPages())
                .plantAddInquiries(result.map(PlantAddInquiryEntity::toDomain).toList())
                .build();
    }

    @Override
    public Optional<PlantAddInquiry> getPlantAddInquiry(Long id) {
        return encyclopediaRepository.findAddInquiryById(id)
                .map(PlantAddInquiryEntity::toDomain);
    }

}
