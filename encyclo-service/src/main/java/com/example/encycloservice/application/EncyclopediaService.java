package com.example.encycloservice.application;

import com.example.common.domain.Passport;
import com.example.common.exception.NotFoundException;
import com.example.encycloservice.adapter.in.request.PlantAddRequest;
import com.example.encycloservice.adapter.out.external.SearchResultByScraper;
import com.example.encycloservice.aop.filter.PassportHolder;
import com.example.encycloservice.application.port.in.EncyclopediaUseCase;
import com.example.encycloservice.application.port.out.EncyclopediaCommand;
import com.example.encycloservice.application.port.out.EncyclopediaQuery;
import com.example.encycloservice.application.port.out.ScraperPort;
import com.example.encycloservice.application.port.out.StatisticsCommand;
import com.example.encycloservice.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EncyclopediaService implements EncyclopediaUseCase {

    private final EncyclopediaCommand encyclopediaCommand;
    private final EncyclopediaQuery encyclopediaQuery;
    private final StatisticsCommand statisticsCommand;
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

    @Transactional
    public void savePlantAddInquiry(PlantAddRequest plantAddRequest) {
        Passport passport = PassportHolder.getPassport();
        PlantAddInquriy plantAddSubmission = PlantAddInquriy.builder()
                .commonName(plantAddRequest.commonName())
                .scientificName(plantAddRequest.scientificName())
                .requesterId(passport.memberId())
                .status(PlantAddInquriy.Status.SUBMITTED)
                .result(null)
                .build();
        encyclopediaCommand.savePlantAddInquiry(plantAddSubmission);
    }

    @Transactional(readOnly = true)
    public PlantBriefListResponse getPlantBriefByIds(List<Long> plantIds) {
        List<PlantBrief> list = plantIds.stream().map(encyclopediaQuery::getBriefById).toList();
        return PlantBriefListResponse.toResponse(list);
    }

    @Transactional(readOnly = true)
    public SearchPlantQueryResult searchByName(String keyword, int page, int size) {
        return encyclopediaQuery.searchByKeyword(keyword, page, size);
    }

    @Transactional(readOnly = true)
    public PlantSpecies getPlantDetailById(Long id) {
        PlantSpecies plantSpecies = encyclopediaQuery.getById(id);
        if (plantSpecies == null) {
            throw new NotFoundException("해당 식물을 찾을 수 없습니다.");
        }
        Passport passport = PassportHolder.getPassport();
        LocalDateTime now = LocalDateTime.now();
        statisticsCommand.recordRecentPlantDetails(plantSpecies, now);
        statisticsCommand.recordPopularPlant(plantSpecies, passport.memberId(), now);
        return plantSpecies;
    }

}
