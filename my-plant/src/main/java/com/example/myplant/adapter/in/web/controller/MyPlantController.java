package com.example.myplant.adapter.in.web.controller;

import com.example.common.annotation.Authenticate;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.Role;
import com.example.myplant.adapter.in.web.request.CreateMyPlantRequest;
import com.example.myplant.adapter.in.web.response.MyPlantListResponse;
import com.example.myplant.adapter.in.web.response.MyPlantResponse;
import com.example.myplant.application.port.in.MyPlantUseCase;
import com.example.myplant.security.PassportHolder;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my-plant")
@Tag(name = "식물 관리", description = "식물 등록 및 관리를 위한 API")
public class MyPlantController {

    private final MyPlantUseCase myPlantUseCase;

    @Tag(name = "내 식물 등록", description = "새로운 식물을 등록합니다.")
    @Authenticate(Role.MEMBER)
    @PostMapping
    public ResponseEntity<CommonResponse<Long>> registerPlant(@RequestBody CreateMyPlantRequest request) {
        Long plantId = myPlantUseCase.createPlant(request.toDomain(PassportHolder.getPassport().memberId()));
        return ResponseEntity.ok(new CommonResponse<>("내 식물이 등록되었습니다.", plantId));
    }

//    @Authenticate(Role.MEMBER)
//    @PutMapping("/update/{plantId}")
//    @Operation(summary = "식물 정보 수정", description = "기존 식물의 정보를 수정합니다.")
//    public ResponseEntity<CommonResponse<Long>> updatePlant(@PathVariable Long plantId, @RequestBody PlantCommand command) {
//        Long updatedPlantId = myPlantUseCase.updatePlant(plantId, command);
//        return ResponseEntity.ok(new CommonResponse<>("Plant updated successfully",updatedPlantId));
//    }
//
    @Tag(name = "내 식물 목록 조회", description = "내 식물의 정보를 목록으로 조회합니다.")
    @Authenticate(Role.MEMBER)
    @GetMapping("/list")
    public ResponseEntity<MyPlantListResponse> getMyPlantList() {
        List<MyPlantResponse> responseList = myPlantUseCase.getMyPlantList().stream()
                .map(MyPlantResponse::from)
                .toList();
        return ResponseEntity.ok(MyPlantListResponse.of(responseList));
    }
//
//    @Authenticate(Role.MEMBER)
//    @GetMapping("/detail/{plantId}")
//    @Operation(summary = "식물 상세 정보 조회", description = "식물의 상세 정보를 조회합니다.")
//    public ResponseEntity<CommonResponse<Plant>> getPlantByPlantId(@PathVariable Long plantId) {
//        Plant plant = myPlantUseCase.getPlantById(plantId);
//        return ResponseEntity.ok(new CommonResponse<>("Plant retrieved successfully",plant));
//    }

}