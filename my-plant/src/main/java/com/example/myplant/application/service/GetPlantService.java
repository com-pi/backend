package com.example.myplant.application.service;

import com.example.myplant.application.port.in.GetPlantUseCase;
import com.example.myplant.application.port.out.FindPlantPort;
import com.example.myplant.domain.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetPlantService implements GetPlantUseCase {

    private final FindPlantPort findPlantPort;

    @Autowired
    public GetPlantService(FindPlantPort findPlantPort) {
        this.findPlantPort = findPlantPort;
    }

    @Override
    public List<Plant> getPlantsByMemberId(Long memberId) {
        return findPlantPort.findPlantsByMemberId(memberId);
    }

    @Override
    public Plant getPlantByPlantId(Long plantId) {
        return findPlantPort.findPlantByPlantId(plantId).orElseThrow(() -> new RuntimeException("Plant not found"));
    }
}
