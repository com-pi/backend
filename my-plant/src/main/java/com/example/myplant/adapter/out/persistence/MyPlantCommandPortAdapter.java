package com.example.myplant.adapter.out.persistence;

import com.example.common.exception.NotFoundException;
import com.example.myplant.adapter.out.persistence.entity.MyPlantEntity;
import com.example.myplant.adapter.out.persistence.entity.PlantCharacterEntity;
import com.example.myplant.application.port.out.MyPlantCommandPort;
import com.example.myplant.domain.MyPlant;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyPlantCommandPortAdapter implements MyPlantCommandPort {

    private final MyPlantRepository myPlantRepository;

    @Override
    public MyPlant save(MyPlant myPlant) {
        try {
            MyPlantEntity myPlantEntity = MyPlantEntity.fromDomain(myPlant);
            return myPlantRepository.save(myPlantEntity).toDomain();
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(PlantCharacterEntity.class);
        }
    }
}
