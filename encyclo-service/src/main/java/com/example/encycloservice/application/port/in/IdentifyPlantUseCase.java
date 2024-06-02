package com.example.encycloservice.application.port.in;

import com.example.encycloservice.adapter.in.response.PlantIdentifyResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IdentifyPlantUseCase {

    PlantIdentifyResponse identifyPlant(List<MultipartFile> images);

}
