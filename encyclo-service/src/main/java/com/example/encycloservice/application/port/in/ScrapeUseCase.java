package com.example.encycloservice.application.port.in;

import com.example.encycloservice.adapter.out.external.PlantDetailResult;
import com.example.encycloservice.adapter.out.external.SearchResultByScraper;

public interface ScrapeUseCase {

    PlantDetailResult scrapePlantDetail(String plantName);
    SearchResultByScraper searchPlant(String keyword);


}
