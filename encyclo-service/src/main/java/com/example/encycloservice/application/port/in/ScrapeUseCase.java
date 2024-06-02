package com.example.encycloservice.application.port.in;

import com.example.encycloservice.adapter.out.PlantDetailResult;
import com.example.encycloservice.adapter.out.SearchPlantResultList;

public interface ScrapeUseCase {

    PlantDetailResult scrapePlantDetail(String plantName);
    SearchPlantResultList searchPlant(String keyword);


}
