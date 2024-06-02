package com.example.encycloservice.application.port.out;

import com.example.encycloservice.adapter.out.PlantIdentificationResult;
import com.example.encycloservice.adapter.out.PlantDetailResult;
import com.example.encycloservice.adapter.out.SearchPlantResultList;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SearchPlantQuery {

    SearchPlantResultList searchPlant(String keyword);
    PlantDetailResult plantDetail(String plantName);
    PlantIdentificationResult identifyPlant(List<MultipartFile> images);

}
