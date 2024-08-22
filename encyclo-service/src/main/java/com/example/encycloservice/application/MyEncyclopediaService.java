package com.example.encycloservice.application;

import com.example.common.exception.NotFoundException;
import com.example.encycloservice.adapter.in.response.MyEncyclopediaDetailResponse;
import com.example.encycloservice.adapter.out.persistence.entity.EncyclopediaPlantEntity;
import com.example.encycloservice.aop.filter.PassportHolder;
import com.example.encycloservice.application.port.in.MyEncyclopediaUseCase;
import com.example.encycloservice.application.port.out.EncyclopediaQuery;
import com.example.encycloservice.application.port.out.MyEncyclopediaCommand;
import com.example.encycloservice.application.port.out.MyEncyclopediaQuery;
import com.example.encycloservice.domain.MyEncyclopedia;
import com.example.encycloservice.domain.MyEncyclopediaCreate;
import com.example.encycloservice.domain.PlantBrief;
import com.example.encycloservice.domain.PlantSpecies;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyEncyclopediaService implements MyEncyclopediaUseCase {

    private final MyEncyclopediaCommand myEncyclopediaCommand;
    private final MyEncyclopediaQuery myEncyclopediaQuery;
    private final EncyclopediaQuery encyclopediaQuery;

    @Override
    public List<MyEncyclopedia> myEncyclopediaList(Long memberId) {
        return myEncyclopediaQuery.getMyEncyclopediaList(memberId);
    }

    @Override
    public MyEncyclopediaDetailResponse getPlantListByEncyclopediaId(Long myEncyclopediaId, Integer page, Integer size) {
        MyEncyclopedia myEncyclopedia = myEncyclopediaQuery.findById(myEncyclopediaId)
                .orElseThrow(() -> new NotFoundException("내 도감을 찾을 수 없습니다."));

        Page<EncyclopediaPlantEntity> pageableResult =
                myEncyclopediaQuery.getPlantListByEncyclopediaId(myEncyclopediaId, page, size);

        List<PlantBrief> plantBriefList = pageableResult.get()
                .map(plant -> encyclopediaQuery.getBriefById(plant.getPlantEntity().getId()))
                .toList();

        return MyEncyclopediaDetailResponse.builder()
                .totalElement(pageableResult.getTotalElements())
                .totalPage(pageableResult.getTotalPages())
                .title(myEncyclopedia.getTitle())
                .coverImageUrl(myEncyclopedia.getCoverImageUrl())
                .plantCollection(plantBriefList)
                .build();
    }

    @Override
    public void createEncyclopedia(MyEncyclopediaCreate myEncyclopediaCreate) {
        MyEncyclopedia myEncyclopedia = MyEncyclopedia.create(myEncyclopediaCreate);
        myEncyclopediaCommand.createEncyclopedia(myEncyclopedia);
    }

    @Override
    @Transactional
    public void addPlantsToEncyclopedia(List<Long> plantSpeciesIds, Long myEncyclopediaId) {
        MyEncyclopedia myEncyclopedia = myEncyclopediaQuery.findById(myEncyclopediaId)
                .orElseThrow(() -> new NotFoundException("내 도감을 찾을 수 없습니다."));
        myEncyclopedia.verifyOwner(PassportHolder.getPassport());

        for (Long plantSpeciesId : plantSpeciesIds) {
            PlantSpecies plantSpecies = encyclopediaQuery.getById(plantSpeciesId);
            if (plantSpecies == null) {
                throw new NotFoundException("식물을 찾을 수 없습니다.");
            }
            try {
                myEncyclopediaCommand.addPlantsToEncyclopedia(plantSpecies, myEncyclopedia);
            } catch (ConstraintViolationException ignored) {
                // 도감에 중복된 식물이 있는 경우 이를 무시합니다.
            }
        }
    }

    @Override
    @Transactional
    public void removePlantsFromEncyclopedia(List<Long> plantSpeciesIds, Long myEncyclopediaId) {
        MyEncyclopedia myEncyclopedia = myEncyclopediaQuery.findById(myEncyclopediaId)
                .orElseThrow(() -> new NotFoundException("내 도감을 찾을 수 없습니다."));
        myEncyclopedia.verifyOwner(PassportHolder.getPassport());

        plantSpeciesIds
                .forEach(plantSpeciesId -> {
                    PlantSpecies plantSpecies = encyclopediaQuery.getById(plantSpeciesId);
                    if (plantSpecies == null) {
                        throw new NotFoundException("식물을 찾을 수 없습니다.");
                    }
                    myEncyclopediaCommand.removePlantFromEncyclopedia(plantSpecies, myEncyclopedia);
                });
    }

    @Override
    @Transactional
    public void removeMyEncyclopedia(Long myEncyclopediaId) {
        MyEncyclopedia myEncyclopedia = myEncyclopediaQuery.findById(myEncyclopediaId)
                .orElseThrow(() -> new NotFoundException("내 도감을 찾을 수 없습니다."));
        myEncyclopedia.verifyOwner(PassportHolder.getPassport());

        myEncyclopediaCommand.removeEncyclopedia(myEncyclopediaId);
    }

    @Override
    @Transactional
    public void movePlantBetweenEncyclopedia(List<Long> plantSpeciesIds, Long fromEncyclopediaId, Long toEncyclopediaId) {
        MyEncyclopedia fromEncyclopedia = myEncyclopediaQuery.findEncyclopediaWithContentById(fromEncyclopediaId)
                .orElseThrow(() -> new NotFoundException("내 도감을 찾을 수 없습니다."));
        MyEncyclopedia toEncyclopedia = myEncyclopediaQuery.findEncyclopedia(toEncyclopediaId)
                .orElseThrow(() -> new NotFoundException("내 도감을 찾을 수 없습니다."));

        fromEncyclopedia.verifyOwner(PassportHolder.getPassport());
        toEncyclopedia.verifyOwner(PassportHolder.getPassport());

        fromEncyclopedia.getPlantCollection().stream()
                .filter(plantBrief -> plantSpeciesIds.contains(plantBrief.getPlantSpeciesId()))
                .forEach(plant -> {
                            myEncyclopediaCommand.removePlantFromEncyclopedia(plant.getPlantSpeciesId(), fromEncyclopedia);
                            try {
                                myEncyclopediaCommand.addPlantsToEncyclopedia(plant.getPlantSpeciesId(), toEncyclopedia);
                            } catch (ConstraintViolationException ignored) {
                                // 이미 도감에 있는 경우 무시합니다.
                            }
                        }
                );
    }
}
