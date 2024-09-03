package com.example.myplant.adapter.out.persistence;

import com.example.common.exception.NotFoundException;
import com.example.myplant.adapter.out.persistence.entity.MyPlantEntity;
import com.example.myplant.application.port.out.MyPlantQueryPort;
import com.example.myplant.domain.MyPlant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MyPlantQueryAdapter implements MyPlantQueryPort {

    private final MyPlantRepository myPlantRepository;

    @Override
    public List<MyPlant> getMyPlantListByMemberId(Long memberId) {
        return myPlantRepository.findByMemberIdAndDeletionYn(memberId, "N").stream()
                .map(MyPlantEntity::toDomain)
                .toList();
    }

    @Override
    public MyPlant getMyPlantByMyPlantId(Long myPlantId) {
        MyPlantEntity myPlantEntity = myPlantRepository.findByMyPlantIdAndDeletionYn(myPlantId, "N")
                .orElseThrow(() -> new NotFoundException(MyPlantEntity.class));
        return myPlantEntity.toDomain();
    }

    @Override
    public String getPlantType(Long myPlantId) {
        MyPlantEntity myPlantEntity = myPlantRepository.findByMyPlantIdAndDeletionYn(myPlantId, "N")
                .orElseThrow(() -> new NotFoundException(MyPlantEntity.class));
        return myPlantEntity.toDomain().getPlantType();
    }

}
