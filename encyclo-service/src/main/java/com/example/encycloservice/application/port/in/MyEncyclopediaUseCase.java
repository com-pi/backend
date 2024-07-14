package com.example.encycloservice.application.port.in;

import com.example.encycloservice.domain.MyEncyclopedia;
import com.example.encycloservice.domain.MyEncyclopediaCreate;

import java.util.List;

public interface MyEncyclopediaUseCase {

    List<MyEncyclopedia> myEncyclopediaList(Long memberId);
    MyEncyclopedia getMyEncyclopediaDetail(Long myEncyclopediaId);

    void createEncyclopedia(MyEncyclopediaCreate myEncyclopediaCreate);
    void addPlantsToEncyclopedia(List<Long> plantSpeciesId, Long myEncyclopediaId);
    void removePlantsFromEncyclopedia(List<Long> plantSpeciesId, Long myEncyclopediaId);

}
