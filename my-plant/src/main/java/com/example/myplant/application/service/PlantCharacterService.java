//package com.example.myplant.application.service;
//
//import com.example.myplant.application.port.in.CreatePlantCharacterUseCase;
//import com.example.myplant.application.port.in.UpdatePlantCharacterUseCase;
//import com.example.myplant.application.port.out.LoadPlantCharacterPort;
//import com.example.myplant.application.port.out.SavePlantCharacterPort;
//import com.example.myplant.domain.PlantCharacter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class PlantCharacterService implements CreatePlantCharacterUseCase, UpdatePlantCharacterUseCase {
//    private final SavePlantCharacterPort savePlantCharacterPort;
//    private final LoadPlantCharacterPort loadPlantCharacterPort;
//
//    @Autowired
//    public PlantCharacterService(SavePlantCharacterPort savePlantCharacterPort, LoadPlantCharacterPort loadPlantCharacterPort) {
//        this.savePlantCharacterPort = savePlantCharacterPort;
//        this.loadPlantCharacterPort = loadPlantCharacterPort;
//    }
//
//    @Override
//    public PlantCharacter createPlantCharacter(PlantCharacter plantCharacter) {
//        return savePlantCharacterPort.savePlantCharacter(plantCharacter);
//    }
//
//    @Override
//    public PlantCharacter updatePlantCharacter(Long id, PlantCharacter plantCharacter) {
//        PlantCharacter existingPlantCharacter = loadPlantCharacterPort.loadPlantCharacterById(id)
//                .orElseThrow(() -> new RuntimeException("Plant character not found"));
//        existingPlantCharacter.setPlant(plantCharacter.getPlant());
//        existingPlantCharacter.setCharacter(plantCharacter.getCharacter());
//        existingPlantCharacter.setIllustration(plantCharacter.getIllustration());
//        return savePlantCharacterPort.savePlantCharacter(existingPlantCharacter);
//    }
//
//    public List<PlantCharacter> getAllPlantCharacters() {
//        return loadPlantCharacterPort.loadAllPlantCharacters();
//    }
//}