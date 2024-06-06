package com.example.myplant.adapter.in.web.controller;

import com.example.common.annotation.Authenticate;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.Role;
import com.example.myplant.application.service.PlantDiaryService;
import com.example.myplant.domain.PlantDiary;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plant-diaries")
@Tag(name = "식물 일지 관리", description = "식물 일지 추가 및 수정 API")
public class PlantDiaryController {
    private final PlantDiaryService plantDiaryService;

    @Autowired
    public PlantDiaryController(PlantDiaryService plantDiaryService) {
        this.plantDiaryService = plantDiaryService;
    }

    @Authenticate(Role.MEMBER)
    @PostMapping("/add")
    @Operation(summary = "식물 일지 추가", description = "새로운 식물 일지를 추가합니다.")
    public ResponseEntity<CommonResponse<PlantDiary>> addPlantDiary(@RequestBody PlantDiary plantDiary) {
        PlantDiary addedPlantDiary = plantDiaryService.createPlantDiary(plantDiary);
        return ResponseEntity.ok(new CommonResponse<>("Plant diary added successfully",addedPlantDiary));
    }

    @Authenticate(Role.MEMBER)
    @PutMapping("/update/{id}")
    @Operation(summary = "식물 일지 수정", description = "기존 식물 일지를 수정합니다.")
    public ResponseEntity<CommonResponse<PlantDiary>> updatePlantDiary(@PathVariable Long id, @RequestBody PlantDiary plantDiaryDetails) {
        PlantDiary updatedPlantDiary = plantDiaryService.updatePlantDiary(id, plantDiaryDetails);
        return ResponseEntity.ok(new CommonResponse<>("Plant diary updated successfully",updatedPlantDiary));
    }

    @Authenticate(Role.MEMBER)
    @GetMapping("/list")
    @Operation(summary = "식물 일지 목록 조회", description = "모든 식물 일지를 조회합니다.")
    public ResponseEntity<CommonResponse<List<PlantDiary>>> getAllPlantDiaries() {
        List<PlantDiary> plantDiaries = plantDiaryService.getAllPlantDiaries();
        return ResponseEntity.ok(new CommonResponse<>("Plant diaries retrieved successfully",plantDiaries));
    }
}
