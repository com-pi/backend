package com.example.encycloservice.application;

import com.example.common.exception.CommonException;
import com.example.encycloservice.adapter.out.PlantDetailResult;
import com.example.encycloservice.adapter.out.SearchPlantResultList;
import com.example.encycloservice.application.port.in.ScrapeUseCase;
import com.example.encycloservice.application.port.out.SearchPlantQuery;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PlantScrapeService implements ScrapeUseCase {

    private final SearchPlantQuery searchPlantQuery;

    @Override
    public PlantDetailResult scrapePlantDetail(String plantName) {
        try {
            return searchPlantQuery.plantDetail(plantName);
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
    public SearchPlantResultList searchPlant(String keyword) {
        try {
            return searchPlantQuery.searchPlant(keyword);
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
