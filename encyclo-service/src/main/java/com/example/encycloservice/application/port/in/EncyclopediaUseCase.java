package com.example.encycloservice.application.port.in;

import com.example.encycloservice.adapter.in.request.PlantAddRequest;
import com.example.encycloservice.adapter.in.response.PlantAddInquiryResponse;
import com.example.encycloservice.application.PlantBriefListResponse;
import com.example.encycloservice.domain.*;

import java.util.List;

public interface EncyclopediaUseCase {

    Long savePlantSpecies(PlantSpeciesCreate plantSpeciesCreate);
    void syncDatabase(String keyword);
    void savePlantAddInquiry(PlantAddRequest plantAddRequest);
    PlantBriefListResponse getPlantBriefByIds(List<Long> plantIds);
    SearchPlantQueryResult searchByName(String keyword, int page, int size);
    PlantSpecies getPlantDetailById(Long id);
    PlantAddInquiryResponse getPlantAddInquiry(Integer page, Integer size, PlantAddInquiry.Status status);

    void processPlantAddInquiry(Long inquiryId, PlantAddInquiryProcess process);
}
