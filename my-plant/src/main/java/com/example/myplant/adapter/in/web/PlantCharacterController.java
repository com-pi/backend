package com.example.myplant.adapter.in.web;

import com.example.common.annotation.Authenticate;
import com.example.common.domain.Role;
import com.example.myplant.domain.PlantCharacter;
import com.example.myplant.application.service.PlantCharacterService;
import com.example.common.baseentity.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plant-characters")
@Tag(name = "식물 캐릭터 관리", description = "식물 캐릭터 추가 및 수정 API")
public class PlantCharacterController {
    private final PlantCharacterService plantCharacterService;

    @Autowired
    public PlantCharacterController(PlantCharacterService plantCharacterService) {
        this.plantCharacterService = plantCharacterService;
    }

    @PostMapping("/add")
    @Authenticate(Role.ADMIN)
    @Operation(summary = "식물 캐릭터 추가", description = "새로운 식물 캐릭터를 추가합니다.")
    public ResponseEntity<CommonResponse<PlantCharacter>> addPlantCharacter(@RequestBody PlantCharacter plantCharacter) {
        PlantCharacter addedPlantCharacter = plantCharacterService.createPlantCharacter(plantCharacter);
        return ResponseEntity.ok(new CommonResponse<>("Plant character added successfully",addedPlantCharacter));
    }

    @PutMapping("/update/{id}")
    @Authenticate(Role.ADMIN)
    @Operation(summary = "식물 캐릭터 수정", description = "기존 식물 캐릭터를 수정합니다.")
    public ResponseEntity<CommonResponse<PlantCharacter>> updatePlantCharacter(@PathVariable Long id, @RequestBody PlantCharacter plantCharacterDetails) {
        PlantCharacter updatedPlantCharacter = plantCharacterService.updatePlantCharacter(id, plantCharacterDetails);
        return ResponseEntity.ok(new CommonResponse<>("Plant character updated successfully",updatedPlantCharacter));
    }

    @GetMapping("/list")
    @Operation(summary = "식물 캐릭터 목록 조회", description = "모든 식물 캐릭터를 조회합니다.")
    public ResponseEntity<CommonResponse<List<PlantCharacter>>> getAllPlantCharacters() {
        List<PlantCharacter> plantCharacters = plantCharacterService.getAllPlantCharacters();
        return ResponseEntity.ok(new CommonResponse<>("Plant characters retrieved successfully",plantCharacters));
    }
}