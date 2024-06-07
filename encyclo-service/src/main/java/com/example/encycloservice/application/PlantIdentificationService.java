package com.example.encycloservice.application;

import com.example.encycloservice.adapter.in.response.PlantIdentifyResponse;
import com.example.encycloservice.adapter.out.external.PlantIdentificationResult;
import com.example.encycloservice.application.port.in.IdentifyPlantUseCase;
import com.example.encycloservice.application.port.out.ScraperPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantIdentificationService implements IdentifyPlantUseCase {

    private final ScraperPort scraperPort;

    @Override
    public PlantIdentifyResponse identifyPlant(List<MultipartFile> images) {
        PlantIdentificationResult result = scraperPort.identifyPlant(images);
        return result.toResponse();
    }

}
