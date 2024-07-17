package com.example.encycloservice.application;

import com.example.common.domain.Passport;
import com.example.common.exception.NotFoundException;
import com.example.encycloservice.domain.PlantAddInquiryProcess;
import com.example.encycloservice.adapter.in.request.PlantAddRequest;
import com.example.encycloservice.adapter.in.response.PlantAddInquiryResponse;
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

    @Override
    @Transactional
    @Async
    public void syncDatabase(String keyword) {
        SearchResultByScraper searchResultByScraper = scraperPort.searchPlant(keyword);
        searchResultByScraper.results().forEach(r -> encyclopediaCommand.syncDatabaseFromExternal(r.id()));
    }

    @Override
    @Transactional
    public void savePlantAddInquiry(PlantAddRequest plantAddRequest) {
        Passport passport = PassportHolder.getPassport();
        PlantAddInquiry plantAddSubmission = PlantAddInquiry.builder()
                .commonName(plantAddRequest.commonName())
                .scientificName(plantAddRequest.scientificName())
                .requesterId(passport.memberId())
                .status(PlantAddInquiry.Status.SUBMITTED)
                .result(null)
                .build();
        encyclopediaCommand.savePlantAddInquiry(plantAddSubmission);
    }

    @Override
    @Transactional(readOnly = true)
    public PlantBriefListResponse getPlantBriefByIds(List<Long> plantIds) {
        List<PlantBrief> list = plantIds.stream().map(encyclopediaQuery::getBriefById).toList();
        return PlantBriefListResponse.toResponse(list);
    }

    @Override
    @Transactional(readOnly = true)
    public SearchPlantQueryResult searchByName(String keyword, int page, int size) {
        return encyclopediaQuery.searchByKeyword(keyword, page, size);
    }

    @Override
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

    @Override
    @Transactional(readOnly = true)
    public PlantAddInquiryResponse getPlantAddInquiry(Integer page, Integer size, PlantAddInquiry.Status status) {
        return encyclopediaQuery.searchPlantAddInquiry(page, size, status);
    }

    @Override
    @Transactional
    public void processPlantAddInquiry(Long inquiryId, PlantAddInquiryProcess process) {
        PlantAddInquiry inquiry = encyclopediaQuery.getPlantAddInquiry(inquiryId)
                        .orElseThrow(() -> new NotFoundException("해당 식물 요청을 찾을 수 없습니다."));
        PlantAddInquiry processed = inquiry.process(process);
        encyclopediaCommand.processPlantAddInquiry(processed);
    }
}
