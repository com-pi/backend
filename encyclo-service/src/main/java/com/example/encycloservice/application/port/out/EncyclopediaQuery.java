package com.example.encycloservice.application.port.out;

import com.example.encycloservice.adapter.in.response.PlantAddInquiryResponse;
import com.example.encycloservice.domain.PlantAddInquiry;
import com.example.encycloservice.domain.PlantBrief;
import com.example.encycloservice.domain.PlantSpecies;
import com.example.encycloservice.domain.SearchPlantQueryResult;

import java.util.Optional;

public interface EncyclopediaQuery {

    SearchPlantQueryResult searchByKeyword(String keyword, int page, int size);
    PlantSpecies getById(Long id);
    PlantBrief getBriefById(Long id);

    PlantAddInquiryResponse searchPlantAddInquiry(Integer page, Integer size, PlantAddInquiry.Status status);

    Optional<PlantAddInquiry> getPlantAddInquiry(Long id);
}
