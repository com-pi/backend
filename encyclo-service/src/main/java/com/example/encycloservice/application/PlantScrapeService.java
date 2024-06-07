package com.example.encycloservice.application;

import com.example.common.exception.CommonException;
import com.example.encycloservice.adapter.out.external.PlantDetailResult;
import com.example.encycloservice.adapter.out.external.SearchResultByScraper;
import com.example.encycloservice.application.port.in.ScrapeUseCase;
import com.example.encycloservice.application.port.out.ScraperPort;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PlantScrapeService implements ScrapeUseCase {

    private final ScraperPort scraperPort;

    @Override
    public PlantDetailResult scrapePlantDetail(String plantName) {
        try {
            return scraperPort.plantDetail(plantName);
        } catch (FeignException e) {
            switch (e.status()) {
                case 404:
                    throw new CommonException("검색 결과가 없습니다.", 404, e);
                default:
                    throw new CommonException("스크랩핑중 에러 발생", 500, e);
            }
        }
    }

    @Override
    public SearchResultByScraper searchPlant(String keyword) {
        try {
            return scraperPort.searchPlant(keyword);
        } catch (FeignException e) {
            switch (e.status()) {
                case 404:
                    throw new CommonException("검색 결과가 없습니다.", 404, e);
                default:
                    throw new CommonException("스크랩핑중 에러 발생", 500, e);
            }
        }
    }
}
