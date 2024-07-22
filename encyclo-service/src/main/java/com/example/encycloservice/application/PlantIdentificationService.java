package com.example.encycloservice.application;

import com.example.encycloservice.adapter.in.response.PlantIdentifyResponse;
import com.example.encycloservice.adapter.out.external.PlantIdentificationResult;
import com.example.encycloservice.adapter.out.external.SearchResultByScraper;
import com.example.encycloservice.application.port.in.IdentifyPlantUseCase;
import com.example.encycloservice.application.port.out.EncyclopediaCommand;
import com.example.encycloservice.application.port.out.EncyclopediaQuery;
import com.example.encycloservice.application.port.out.ScraperPort;
import com.example.encycloservice.domain.PlantBrief;
import com.example.encycloservice.domain.SearchPlantByKeywordResult;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantIdentificationService implements IdentifyPlantUseCase {

    private final ScraperPort scraperPort;
    private final EncyclopediaQuery encyclopediaQuery;
    private final EncyclopediaCommand encyclopediaCommand;

    @Override
    public PlantIdentifyResponse identifyPlant(List<MultipartFile> images) {

        // fixme: 리펙토링

        PlantIdentificationResult identificationResult = scraperPort.identifyPlant(images);
        List<PlantBrief> candidates = new ArrayList<>();

        for(var candidate : identificationResult.getResults()){
            encyclopediaQuery.searchBySpecies(
                    candidate.getSpecies().getScientificNameWithoutAuthor(),
                    candidate.getSpecies().getGenus().getScientificNameWithoutAuthor()
            ).ifPresent(p -> candidates.add(p.toBrief()));
        }

        if(!candidates.isEmpty()) {
            return PlantIdentifyResponse.builder()
                    .identificationResults(identificationResult.toResponse())
                    .dbResults(candidates)
                    .build();
        }

        for(var candidate : identificationResult.getResults()){
            SearchResultByScraper searchResultByScraper;
            try {
                searchResultByScraper = scraperPort.searchPlant(candidate.getSpecies().getScientificNameWithoutAuthor().split(" ")[1]);
            } catch (FeignException.NotFound e){
                continue;
            }
            searchResultByScraper.results().forEach(r -> encyclopediaCommand.syncDatabaseFromExternal(r.id()));
            for(var result : searchResultByScraper.results()){
                SearchPlantByKeywordResult searchPlantByKeywordResult = encyclopediaQuery.searchByKeyword(result.name(), 0, 10);
                searchPlantByKeywordResult.getResults().forEach(p ->
                        candidates.add(encyclopediaQuery.getBriefById(p.getPlantSpeciesId()))
                );
            }
            if(!candidates.isEmpty()) {
                break;
            }
        }

        return PlantIdentifyResponse.builder()
                .identificationResults(identificationResult.toResponse())
                .dbResults(candidates)
                .build();
    }

}
