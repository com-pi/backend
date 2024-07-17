package com.example.encycloservice.adapter.in.fake;

import com.example.encycloservice.application.port.out.EncyclopediaCommand;
import com.example.encycloservice.domain.PlantAddInquiry;
import com.example.encycloservice.domain.PlantSpecies;

import java.util.ArrayList;
import java.util.List;

public class FakeEncyclopediaCommand implements EncyclopediaCommand {

    public List<String> ids = new ArrayList<>();

    @Override
    public Long savePlantSpecies(PlantSpecies plantSpecies) {
        return null;
    }

    @Override
    public void syncDatabaseFromExternal(String id) {
        ids.add(id);
    }

    @Override
    public void savePlantAddInquiry(PlantAddInquiry plantAddInquiry) {
    }

    @Override
    public void processPlantAddInquiry(PlantAddInquiry processed) {
    }

}
