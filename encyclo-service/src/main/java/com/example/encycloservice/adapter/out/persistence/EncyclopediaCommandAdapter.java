package com.example.encycloservice.adapter.out.persistence;

import com.example.common.exception.InternalServerException;
import com.example.encycloservice.adapter.out.external.PlantDetailResult;
import com.example.encycloservice.adapter.out.external.PlantInfoScraper;
import com.example.encycloservice.adapter.out.persistence.entity.PlantAddInquiryEntity;
import com.example.encycloservice.adapter.out.persistence.entity.PlantSpeciesEntity;
import com.example.encycloservice.adapter.out.persistence.repository.EncyclopediaRepository;
import com.example.encycloservice.application.port.out.EncyclopediaCommand;
import com.example.encycloservice.domain.PlantAddInquiry;
import com.example.encycloservice.domain.PlantSpecies;
import feign.FeignException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EncyclopediaCommandAdapter implements EncyclopediaCommand {

    private final EntityManager entityManager;
    private final EncyclopediaRepository encyclopediaRepository;
    private final PlantInfoScraper plantInfoScraper;

    @Override
    public Long savePlantSpecies(PlantSpecies plantSpecies) {
        PlantSpeciesEntity saved = encyclopediaRepository.save(PlantSpeciesEntity.fromDomain(plantSpecies));
        encyclopediaRepository.flush();
        return saved.getId();
    }

    @Override
    public void syncDatabaseFromExternal(String id) {
        try {
            PlantDetailResult plantDetailResult = plantInfoScraper.plantDetail(id);
            if (encyclopediaRepository.existsByCommonName(plantDetailResult.name())){
                return;
            }
            PlantSpecies plantSpecies = PlantSpecies.create(plantDetailResult.toDomainCreate());
            encyclopediaRepository.save(PlantSpeciesEntity.fromDomain(plantSpecies));
        } catch (FeignException ignored) {
        }
    }

    @Override
    public void savePlantAddInquiry(PlantAddInquiry plantAddInquriy) {
        try {
            entityManager.persist(PlantAddInquiryEntity.fromDomain(plantAddInquriy));
            entityManager.flush();
        } catch (Exception e) {
            throw new InternalServerException("식물 추가 요청 실패", e);
        }
    }

    @Override
    public void processPlantAddInquiry(PlantAddInquiry processed) {
        PlantAddInquiryEntity entity = encyclopediaRepository.findAddInquiryById(processed.id())
                .orElseThrow(() -> new InternalServerException("식물 추가 요청을 찾을 수 없습니다.", null));
        entity.update(processed);
    }

}
