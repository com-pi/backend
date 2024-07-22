package com.example.encycloservice.application.port.out;

import com.example.encycloservice.adapter.out.external.PlantDetailResult;
import com.example.encycloservice.adapter.out.external.PlantIdentificationResult;
import com.example.encycloservice.adapter.out.external.SearchResultByScraper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ScraperPort {

    SearchResultByScraper searchPlant(String keyword);
    PlantDetailResult plantDetail(String plantName);
    PlantIdentificationResult identifyPlant(List<MultipartFile> images);

}
