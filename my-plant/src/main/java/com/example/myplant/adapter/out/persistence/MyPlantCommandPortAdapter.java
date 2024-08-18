package com.example.myplant.adapter.out.persistence;

import com.example.common.exception.NotFoundException;
import com.example.myplant.adapter.out.persistence.entity.MyPlantEntity;
import com.example.myplant.adapter.out.persistence.entity.PlantCharacterEntity;
import com.example.myplant.application.port.out.MyPlantCommandPort;
import com.example.myplant.domain.MyPlant;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyPlantCommandPortAdapter implements MyPlantCommandPort {

    private final MyPlantRepository myPlantRepository;
    private final PlantCharacterRepository plantCharacterRepository;

    @Override
    public MyPlant save(MyPlant myPlant) {
        try {
            MyPlantEntity myPlantEntity = MyPlantEntity.fromDomain(myPlant);
            return myPlantRepository.save(myPlantEntity).toDomain();
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(PlantCharacterEntity.class);
        }
    }

    @Override
    public void update(MyPlant myPlant) {
        MyPlantEntity myPlantEntity = myPlantRepository.findById(myPlant.getMyPlantId())
                .orElseThrow(() -> new NotFoundException(MyPlantEntity.class));
        myPlantEntity.update(myPlant);
    }

    @Override
    public void delete(Long myPlantId) {
        MyPlantEntity myPlantEntity = myPlantRepository.findById(myPlantId)
                .orElseThrow(() -> new NotFoundException(MyPlantEntity.class));
        myPlantEntity.delete();
    }

    @Override
    public void updatePlantCharacter(MyPlant myPlant) {
        PlantCharacterEntity plantCharacterEntity = plantCharacterRepository.findById(myPlant.getPlantCharacterId())
                .orElseThrow(() -> new NotFoundException(PlantCharacterEntity.class));
        MyPlantEntity myPlantEntity = myPlantRepository.findById(myPlant.getMyPlantId())
                .orElseThrow(() -> new NotFoundException(MyPlantEntity.class));
        myPlantEntity.updatePlantCharacter(plantCharacterEntity);
    }

}
