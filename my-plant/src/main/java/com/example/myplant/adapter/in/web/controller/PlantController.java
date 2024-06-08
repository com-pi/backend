package com.example.myplant.adapter.in.web.controller;

import com.example.common.annotation.Authenticate;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.Passport;
import com.example.common.domain.Role;
import com.example.myplant.adapter.in.web.command.PlantCommand;
import com.example.myplant.application.service.PlantService;
import com.example.myplant.domain.Plant;
import com.example.myplant.security.PassportHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "식물 관리", description = "식물 등록 및 관리를 위한 API")
public class PlantController {

    private final PlantService plantService;
    private final ObjectMapper objectMapper;

    @Autowired
    public PlantController(PlantService plantService, ObjectMapper objectMapper) {
        this.plantService = plantService;
        this.objectMapper = objectMapper;

    }

    @Authenticate(Role.MEMBER)
    @PostMapping("/register")
    @Operation(summary = "식물 등록", description = "새로운 식물을 등록합니다.")
    public ResponseEntity<CommonResponse<Long>> registerPlant(@RequestBody PlantCommand command) {

        Long registeredPlantId = plantService.createPlant(command);
        return ResponseEntity.ok(new CommonResponse<>("Plant registered successfully",registeredPlantId));
    }

    @Authenticate(Role.MEMBER)
    @PutMapping("/update/{plantId}")
    @Operation(summary = "식물 정보 수정", description = "기존 식물의 정보를 수정합니다.")
    public ResponseEntity<CommonResponse<Long>> updatePlant(@PathVariable Long plantId, @RequestBody PlantCommand command) {
        Long updatedPlantId = plantService.updatePlant(plantId, command);
        return ResponseEntity.ok(new CommonResponse<>("Plant updated successfully",updatedPlantId));
    }

    @Authenticate(Role.MEMBER)
    @GetMapping("/list")
    @Operation(summary = "사용자의 모든 식물 조회", description = "사용자가 등록한 모든 식물 정보를 조회합니다.")
    public ResponseEntity<CommonResponse<List<Plant>>> getPlantsByMemberId() {
        Passport passport = PassportHolder.getPassport();
        Long memberId = passport.memberId();

        List<Plant> plants = plantService.getPlantsByMemberId(memberId);
        return ResponseEntity.ok(new CommonResponse<>("Plants retrieved successfully",plants));
    }

    @Authenticate(Role.MEMBER)
    @GetMapping("/detail/{plantId}")
    @Operation(summary = "식물 상세 정보 조회", description = "식물의 상세 정보를 조회합니다.")
    public ResponseEntity<CommonResponse<Plant>> getPlantByPlantId(@PathVariable Long plantId) {
        Plant plant = plantService.getPlantById(plantId);
        return ResponseEntity.ok(new CommonResponse<>("Plant retrieved successfully",plant));
    }

}