package com.example.encycloservice.adapter.out;

import com.example.encycloservice.application.port.out.SearchPlantQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SearchPlantAdapter implements SearchPlantQuery {

    private final PlantInfoScraper plantInfoScraper;
    private final PlantIdentifier plantIdentifier;
    // Todo app key 관리
    private final String appKey = "2b10OsIi0nKjga2Wggytf9xmEu";

    @Override
    public SearchPlantResultList searchPlant(String keyword){
        return plantInfoScraper.searchPlant(keyword);
    }

    @Override
    public PlantDetailResult plantDetail(String plantName) {
        return plantInfoScraper.plantDetail(plantName);
    }

    @Override
    public PlantIdentificationResult identifyPlant(List<MultipartFile> images) {
        return plantIdentifier.identify(
                "all",
                appKey,
                images
        );
    }


}
