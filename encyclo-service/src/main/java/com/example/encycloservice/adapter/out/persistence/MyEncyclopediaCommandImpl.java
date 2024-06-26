package com.example.encycloservice.adapter.out.persistence;

import com.example.encycloservice.adapter.out.persistence.entity.EncyclopediaPlantEntity;
import com.example.encycloservice.adapter.out.persistence.entity.MyEncyclopediaEntity;
import com.example.encycloservice.adapter.out.persistence.entity.PlantSpeciesEntity;
import com.example.encycloservice.application.port.out.MyEncyclopediaCommand;
import com.example.encycloservice.domain.MyEncyclopedia;
import com.example.encycloservice.domain.PlantSpecies;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MyEncyclopediaCommandImpl implements MyEncyclopediaCommand {

    private final MyEncyclopediaRepository myEncyclopediaRepository;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public void createEncyclopedia(MyEncyclopedia myEncyclopedia) {
        myEncyclopediaRepository.save(MyEncyclopediaEntity.fromDomain(myEncyclopedia));
    }

    @Override
    public void updateEncyclopedia(MyEncyclopedia myEncyclopedia) {

    }

    @Override
    @Transactional
    public void addPlantToEncyclopedia(PlantSpecies plantSpecies, MyEncyclopedia myEncyclopedia) {
        EncyclopediaPlantEntity encyclopediaPlant = EncyclopediaPlantEntity.builder()
                .plantEntity(entityManager.getReference(PlantSpeciesEntity.class, plantSpecies.getId()))
                .myEncyclopediaEntity(entityManager.getReference(MyEncyclopediaEntity.class, myEncyclopedia.getId()))
                .build();
        entityManager.persist(encyclopediaPlant);
    }

    @Override
    @Transactional
    public void removePlantFromEncyclopedia(PlantSpecies plantSpecies, MyEncyclopedia myEncyclopedia) {
        myEncyclopediaRepository.deleteByPlantSpeciesIdAndMyEncyclopediaId(plantSpecies.getId(), myEncyclopedia.getId());
    }

}
