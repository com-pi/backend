package com.example.myplant.application.service;

import com.example.myplant.application.port.in.RegistPlantUseCase;
import com.example.myplant.application.port.out.ImageUploadPort;
import com.example.myplant.application.port.out.SavePlantPort;
import com.example.myplant.adapter.in.web.PlantCommand;
import com.example.myplant.domain.MaintenanceSchedule;
import com.example.myplant.domain.Plant;
import com.example.myplant.domain.WateringInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistPlantService implements RegistPlantUseCase {

    private final SavePlantPort savePlantPort;
    private final ImageUploadPort imageUploadPort;

    @Autowired
    public RegistPlantService(SavePlantPort savePlantPort,ImageUploadPort imageUploadPort) {
        this.savePlantPort = savePlantPort;
        this.imageUploadPort = imageUploadPort;
    }

    @Override
    public Plant registPlant(PlantCommand command) {
        command.validate();
        List<String> uploadedImageUrls = imageUploadPort.uploadImages(command.getPlantImageUrls());
        Plant plant = Plant.builder()
                .memberId(command.getMemberId())
                .plantName(command.getPlantName())
                .plantType(command.getPlantType())
                .plantAge(command.getPlantAge())
                .plantBirthday(command.getPlantBirthday())
                .plantImageUrls(uploadedImageUrls)
                .wateringInfo(new WateringInfo(command.getWateringIntervalInWeeks(),command.getWateringFrequency()))
                .maintenanceSchedule(new MaintenanceSchedule(command.getRepottingDate(), command.getFertilizingDate(), command.getPruningDate()))
                .plantLocation(command.getPlantLocation())
                .potType(command.getPotType())
                .build();
        return savePlantPort.savePlant(plant);
    }
}