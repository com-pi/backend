package com.example.encycloservice.application;

import com.example.common.exception.NotFoundException;
import com.example.encycloservice.adapter.out.external.SearchResultByScraper;
import com.example.encycloservice.application.port.out.EncyclopediaCommand;
import com.example.encycloservice.application.port.out.EncyclopediaQuery;
import com.example.encycloservice.application.port.out.ScraperPort;
import com.example.encycloservice.domain.PlantSpecies;
import com.example.encycloservice.domain.PlantSpeciesCreate;
import com.example.encycloservice.domain.SearchPlantQueryResult;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EncyclopediaService {

    private final EncyclopediaCommand encyclopediaCommand;
    private final EncyclopediaQuery encyclopediaQuery;
    private final ScraperPort scraperPort;

    @Transactional
    public Long savePlantSpecies(PlantSpeciesCreate plantSpeciesCreate) {
        return encyclopediaCommand.savePlantSpecies(PlantSpecies.create(plantSpeciesCreate));
    }

    @Transactional
    public int syncDatabase(String keyword) {
        try {
            SearchResultByScraper searchResultByScraper = scraperPort.searchPlant(keyword);
            return searchResultByScraper.results().stream()
                    .mapToInt(r -> encyclopediaCommand.syncDatabaseFromExternal(r.id()))
                    .sum();
        } catch (FeignException e) {
            return 0;
        }
    }

    @Transactional(readOnly = true)
    public SearchPlantQueryResult searchByName(String keyword, int page, int size) {
        return encyclopediaQuery.searchByKeyword(keyword, page, size);
    }

    @Transactional(readOnly = true)
    public PlantSpecies getPlantDetailByName(Long id) {
        return encyclopediaQuery.findById(id)
                .orElseThrow(() -> new NotFoundException("Plant with id " + id + " not found"));
    }

}
