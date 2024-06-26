package com.example.encycloservice.application;

import com.example.common.exception.NotFoundException;
import com.example.encycloservice.aop.filter.PassportHolder;
import com.example.encycloservice.application.port.in.MyEncyclopediaUseCase;
import com.example.encycloservice.application.port.out.EncyclopediaQuery;
import com.example.encycloservice.application.port.out.MyEncyclopediaCommand;
import com.example.encycloservice.application.port.out.MyEncyclopediaQuery;
import com.example.encycloservice.domain.MyEncyclopedia;
import com.example.encycloservice.domain.MyEncyclopediaCreate;
import com.example.encycloservice.domain.PlantSpecies;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public MyEncyclopedia getMyEncyclopediaDetail(Long myEncyclopediaId) {
        return myEncyclopediaQuery.findEncyclopediaWithContentById(myEncyclopediaId)
                .orElseThrow(() -> new NotFoundException("내 도감을 찾을 수 없습니다."));
    }

    @Override
    public void createEncyclopedia(MyEncyclopediaCreate myEncyclopediaCreate) {
        MyEncyclopedia myEncyclopedia = MyEncyclopedia.create(myEncyclopediaCreate);
        myEncyclopediaCommand.createEncyclopedia(myEncyclopedia);
    }

    @Override
    public void addPlantToEncyclopedia(Long plantSpeciesId, Long myEncyclopediaId) {
        PlantSpecies plantSpecies = encyclopediaQuery.getById(plantSpeciesId);
        if(plantSpecies == null) {
            throw new NotFoundException("식물을 찾을 수 없습니다.");
        }

        MyEncyclopedia myEncyclopedia = myEncyclopediaQuery.findById(myEncyclopediaId)
                .orElseThrow(() -> new NotFoundException("내 도감을 찾을 수 없습니다."));
        myEncyclopedia.verifyOwner(PassportHolder.getPassport());

        myEncyclopediaCommand.addPlantToEncyclopedia(plantSpecies, myEncyclopedia);
    }

    @Override
    public void removePlantFromEncyclopedia(Long myEncyclopediaId, Long plantSpeciesId) {
        MyEncyclopedia myEncyclopedia = myEncyclopediaQuery.findById(myEncyclopediaId)
                .orElseThrow(() -> new NotFoundException("내 도감을 찾을 수 없습니다."));
        myEncyclopedia.verifyOwner(PassportHolder.getPassport());

        PlantSpecies plantSpecies = encyclopediaQuery.getById(plantSpeciesId);
        if(plantSpecies == null) {
            throw new NotFoundException("식물을 찾을 수 없습니다.");
        }

        myEncyclopediaCommand.removePlantFromEncyclopedia(plantSpecies, myEncyclopedia);
    }

}
