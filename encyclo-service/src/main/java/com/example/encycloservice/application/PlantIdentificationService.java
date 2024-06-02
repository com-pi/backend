package com.example.encycloservice.application;

import com.example.encycloservice.adapter.in.response.PlantIdentifyResponse;
import com.example.encycloservice.adapter.out.PlantIdentificationResult;
import com.example.encycloservice.application.port.in.IdentifyPlantUseCase;
import com.example.encycloservice.application.port.out.SearchPlantQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantIdentificationService implements IdentifyPlantUseCase {

    private final SearchPlantQuery searchPlantQuery;

    @Override
    public PlantIdentifyResponse identifyPlant(List<MultipartFile> images) {
        PlantIdentificationResult result = searchPlantQuery.identifyPlant(images);
        return result.toResponse();
    }
}
