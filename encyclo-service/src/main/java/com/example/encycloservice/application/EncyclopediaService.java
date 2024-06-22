package com.example.encycloservice.application;

import com.example.common.exception.NotFoundException;
import com.example.encycloservice.adapter.out.external.SearchResultByScraper;
import com.example.encycloservice.application.port.in.EncyclopediaUseCase;
import com.example.encycloservice.application.port.out.EncyclopediaStatCommand;
import com.example.encycloservice.application.port.out.EncyclopediaCommand;
import com.example.encycloservice.application.port.out.EncyclopediaQuery;
import com.example.encycloservice.application.port.out.ScraperPort;
import com.example.encycloservice.domain.PlantSpecies;
import com.example.encycloservice.domain.PlantSpeciesCreate;
import com.example.encycloservice.domain.SearchPlantQueryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EncyclopediaService implements EncyclopediaUseCase {

    private final EncyclopediaCommand encyclopediaCommand;
    private final EncyclopediaQuery encyclopediaQuery;
    private final EncyclopediaStatCommand encyclopediaStatCommand;
    private final ScraperPort scraperPort;

    @Transactional
    public Long savePlantSpecies(PlantSpeciesCreate plantSpeciesCreate) {
        return encyclopediaCommand.savePlantSpecies(PlantSpecies.create(plantSpeciesCreate));
    }

    @Transactional
    @Async
    public void syncDatabase(String keyword) {
        SearchResultByScraper searchResultByScraper = scraperPort.searchPlant(keyword);
        searchResultByScraper.results().forEach(r -> encyclopediaCommand.syncDatabaseFromExternal(r.id()));
    }

    @Transactional(readOnly = true)
    public SearchPlantQueryResult searchByName(String keyword, int page, int size) {
        encyclopediaStatCommand.recordRecentSearchKeyword(keyword, System.currentTimeMillis());
        return encyclopediaQuery.searchByKeyword(keyword, page, size);
    }

    @Transactional(readOnly = true)
    public PlantSpecies getPlantDetailById(Long id) {
        PlantSpecies plantSpecies = encyclopediaQuery.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 식물을 찾을 수 없습니다."));
        encyclopediaStatCommand.recordRecentPlantDetails(plantSpecies, System.currentTimeMillis());
        return plantSpecies;
    }

}
