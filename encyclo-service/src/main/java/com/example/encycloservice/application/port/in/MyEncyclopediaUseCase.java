package com.example.encycloservice.application.port.in;

import com.example.encycloservice.adapter.in.response.MyEncyclopediaDetailResponse;
import com.example.encycloservice.domain.MyEncyclopedia;
import com.example.encycloservice.domain.MyEncyclopediaCreate;

import java.util.List;

public interface MyEncyclopediaUseCase {

    List<MyEncyclopedia> myEncyclopediaList(Long memberId);
    MyEncyclopediaDetailResponse getPlantListByEncyclopediaId(Long myEncyclopediaId, Integer page, Integer size);

    void createEncyclopedia(MyEncyclopediaCreate myEncyclopediaCreate);
    void addPlantsToEncyclopedia(List<Long> plantSpeciesId, Long myEncyclopediaId);
    void removePlantsFromEncyclopedia(List<Long> plantSpeciesId, Long myEncyclopediaId);
    void removeMyEncyclopedia(Long myEncyclopediaId);
    void movePlantBetweenEncyclopedia(List<Long> plantSpeciesIds, Long fromEncyclopediaId, Long toEncyclopediaId);

}
