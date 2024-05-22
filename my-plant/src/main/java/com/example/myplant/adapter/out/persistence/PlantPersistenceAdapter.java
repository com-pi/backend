package com.example.myplant.adapter.out.persistence;

import com.example.myplant.application.port.out.FindPlantPort;
import com.example.myplant.application.port.out.SavePlantPort;
import com.example.myplant.domain.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PlantPersistenceAdapter implements SavePlantPort, FindPlantPort {

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
    public Optional<Plant> findPlantById(Long id) {
        return plantRepository.findById(id);
    }

    @Override
    public List<Plant> findPlantsByMemberId(Long memberId) {
        return plantRepository.findByMemberId(memberId);
    }

    @Override
    public Optional<Plant> findPlantByPlantId(Long plantId) {
        return plantRepository.findByPlantId(plantId);
    }
}