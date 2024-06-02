package com.example.myplant.adapter.in.web;

import com.example.common.annotation.Authenticate;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.Passport;
import com.example.common.domain.Role;
import com.example.myplant.application.port.in.GetPlantUseCase;
import com.example.myplant.application.port.in.RegistPlantUseCase;
import com.example.myplant.application.port.in.UpdatePlantUseCase;
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
public class MyPlantController {

    private final RegistPlantUseCase registPlantUseCase;
    private final UpdatePlantUseCase updatePlantUseCase;
    private final GetPlantUseCase getPlantUseCase;
    private final ObjectMapper objectMapper;

    @Autowired
    public MyPlantController(RegistPlantUseCase registPlantUseCase, UpdatePlantUseCase updatePlantUseCase, GetPlantUseCase getPlantUseCase, ObjectMapper objectMapper) {
        this.registPlantUseCase = registPlantUseCase;
        this.updatePlantUseCase = updatePlantUseCase;
        this.getPlantUseCase = getPlantUseCase;
        this.objectMapper = objectMapper;

    }

    @Authenticate(Role.MEMBER)
    @PostMapping("/register")
    @Operation(summary = "식물 등록", description = "새로운 식물을 등록합니다.")
    public ResponseEntity<CommonResponse<Long>> registerPlant(@RequestBody PlantCommand command) {
        Passport passport = PassportHolder.getPassport();
        command.setMemberId(passport.memberId());

        Plant registeredPlant = registPlantUseCase.registerPlant(command);
        return ResponseEntity.ok(new CommonResponse<>("Plant registered successfully",registeredPlant.getId()));
    }

    @PutMapping("/update/{plantId}")
    @Operation(summary = "식물 정보 수정", description = "기존 식물의 정보를 수정합니다.")
    public ResponseEntity<CommonResponse<Long>> updatePlant(@PathVariable Long plantId, @RequestBody UpdatePlantCommand command) {
        Plant updatedPlant = updatePlantUseCase.updatePlant(plantId, command);
        return ResponseEntity.ok(new CommonResponse<>("Plant updated successfully",updatedPlant.getId()));
    }

    @GetMapping("/list")
    @Operation(summary = "사용자의 모든 식물 조회", description = "사용자가 등록한 모든 식물 정보를 조회합니다.")
    public ResponseEntity<CommonResponse<List<Plant>>> getPlantsByMemberId() {
        Passport passport = PassportHolder.getPassport();
        Long memberId = passport.memberId();

        List<Plant> plants = getPlantUseCase.getPlantsByMemberId(memberId);
        return ResponseEntity.ok(new CommonResponse<>("Plants retrieved successfully",plants));
    }

    @GetMapping("/detail/{plantId}")
    @Operation(summary = "식물 상세 정보 조회", description = "식물의 상세 정보를 조회합니다.")
    public ResponseEntity<CommonResponse<Plant>> getPlantByPlantId(@PathVariable Long plantId) {
        Plant plant = getPlantUseCase.getPlantByPlantId(plantId);
        return ResponseEntity.ok(new CommonResponse<>("Plant retrieved successfully",plant));
    }
}