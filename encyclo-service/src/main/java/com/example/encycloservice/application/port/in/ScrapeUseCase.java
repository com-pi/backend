package com.example.encycloservice.application.port.in;

import com.example.encycloservice.domain.PlantDetailResult;
import com.example.encycloservice.domain.SearchPlantResultList;

public interface ScrapeUseCase {

    PlantDetailResult scrapePlantDetail(String plantName);
    SearchPlantResultList searchPlant(String keyword);

}
