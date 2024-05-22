package com.example.encycloservice.application;

import com.example.encycloservice.application.port.in.ScrapeUseCase;
import com.example.encycloservice.application.port.out.ScrapingPort;
import com.example.encycloservice.domain.PlantDetailResult;
import com.example.encycloservice.domain.SearchPlantResultList;
import com.example.common.exception.CommonException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PlantScrapeService implements ScrapeUseCase {

    private final ScrapingPort scrapingPort;

    @Override
    public PlantDetailResult scrapePlantDetail(String plantName) {
        try {
            return scrapingPort.plantDetail(plantName);
        } catch (FeignException e) {
            switch (e.status()) {
                case 404:
                    throw new CommonException("검색 결과가 없습니다.", 404);
                default:
                    throw new CommonException("스크랩핑중 에러 발생", 500);
            }
        }
    }

    @Override
    public SearchPlantResultList searchPlant(String keyword) {
        try {
            return scrapingPort.searchPlant(keyword);
        } catch (FeignException e) {
            switch (e.status()) {
                case 404:
                    throw new CommonException("검색 결과가 없습니다.", 404);
                default:
                    throw new CommonException("스크랩핑중 에러 발생", 500);
            }
        }
    }
}
