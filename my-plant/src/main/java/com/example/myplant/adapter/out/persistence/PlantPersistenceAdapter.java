package com.example.myplant.adapter.out.persistence;

import com.example.myplant.application.port.out.LoadPlantPort;
import com.example.myplant.application.port.out.SavePlantPort;
import com.example.myplant.domain.Plant;
import com.example.myplant.adapter.out.persistence.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PlantPersistenceAdapter implements SavePlantPort, LoadPlantPort {

    private final PlantRepository plantRepository;

    @Autowired
    public PlantPersistenceAdapter(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    @Override
    public Plant savePlant(Plant plant) {
        return plantRepository.save(plant);
    }

    @Override
    public Optional<Plant> loadPlantById(Long id) {
        return plantRepository.findById(id);
    }
}