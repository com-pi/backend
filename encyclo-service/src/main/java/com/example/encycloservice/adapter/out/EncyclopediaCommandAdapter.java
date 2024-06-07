package com.example.encycloservice.adapter.out;

import com.example.encycloservice.adapter.out.external.PlantDetailResult;
import com.example.encycloservice.adapter.out.external.PlantInfoScraper;
import com.example.encycloservice.adapter.out.persistence.EncyclopediaRepository;
import com.example.encycloservice.adapter.out.persistence.PlantSpeciesEntity;
import com.example.encycloservice.application.port.out.EncyclopediaCommand;
import com.example.encycloservice.domain.PlantSpecies;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EncyclopediaCommandAdapter implements EncyclopediaCommand {

    private final EncyclopediaRepository encyclopediaRepository;
    private final PlantInfoScraper plantInfoScraper;

    @Override
    public Long savePlantSpecies(PlantSpecies plantSpecies) {
        PlantSpeciesEntity saved = encyclopediaRepository.save(PlantSpeciesEntity.fromDomain(plantSpecies));
        encyclopediaRepository.flush();
        return saved.getId();
    }

    @Override
    public int syncDatabaseFromExternal(String id) {
        try {
            PlantDetailResult plantDetailResult = plantInfoScraper.plantDetail(id);
            if(encyclopediaRepository.existsByCommonName(plantDetailResult.name())){
                return 0;
            }
            PlantSpecies plantSpecies = PlantSpecies.create(plantDetailResult.toDomainCreate());
            encyclopediaRepository.save(PlantSpeciesEntity.fromDomain(plantSpecies));
        } catch (FeignException e) {
            return 0;
        }
        return 1;
    }
}
