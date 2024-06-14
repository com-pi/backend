package com.example.myplant.adapter.in.web.controller;

import com.example.common.annotation.Authenticate;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.Role;
import com.example.myplant.adapter.in.web.request.CreateMyPlantRequest;
import com.example.myplant.adapter.in.web.request.UpdateMyPlantRequest;
import com.example.myplant.adapter.in.web.response.MyPlantDetailResponse;
import com.example.myplant.adapter.in.web.response.MyPlantListResponse;
import com.example.myplant.adapter.in.web.response.MyPlantResponse;
import com.example.myplant.application.port.in.MyPlantUseCase;
import com.example.myplant.domain.MyPlant;
import com.example.myplant.security.PassportHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "식물 관리", description = "식물 등록 및 관리를 위한 API")
public class MyPlantController {

    private final MyPlantUseCase myPlantUseCase;

    @Operation(summary = "내 식물 등록", description = "새로운 식물을 등록합니다.")
    @Authenticate(Role.MEMBER)
    @PostMapping
    public ResponseEntity<CommonResponse<Long>> registerPlant(
            @Schema(
                    example = "{\"plantName\":\"테스트 식물\",\"plantType\":\"장미\"," +
                            "\"plantBirthday\":\"2024-06-05\",\"lastWaterday\":\"2024-06-06\"," +
                            "\"wateringIntervalInDays\":10,\"plantLocation\":\"dark\",\"potType\":\"hydro\"," +
                            "\"characterId\":1}"
            )
            @RequestBody CreateMyPlantRequest request) {
        Long memberId = PassportHolder.getPassport().memberId();
        Long plantId = myPlantUseCase.createPlant(request.toDomain(memberId));
        return ResponseEntity.ok(new CommonResponse<>("내 식물이 등록되었습니다.", plantId));
    }

    @Operation(summary = "내 식물 수정", description = "기존 식물의 정보를 수정합니다.")
    @Authenticate(Role.MEMBER)
    @PatchMapping
    public ResponseEntity<CommonResponse<Long>> updatePlant(
            @Schema(
                    example = "{\"myPlantId\":\"1\",\"plantName\":\"수정 식물\",\"plantType\":\"들국화\"," +
                            "\"plantBirthday\":\"2024-06-05\",\"lastWaterday\":\"2024-06-06\"," +
                            "\"wateringIntervalInDays\":10,\"plantLocation\":\"dark\",\"potType\":\"hydro\"," +
                            "\"characterId\":1}"
            )
            @RequestBody UpdateMyPlantRequest request) {
        Long plantId = myPlantUseCase.updateMyPlant(request.toDomain(PassportHolder.getPassport().memberId()));
        return ResponseEntity.ok(new CommonResponse<>("내 식물이 수정되었습니다.", plantId));
    }

    @Operation(summary = "내 식물 삭제", description = "기존 식물을 삭제합니다.")
    @Authenticate(Role.MEMBER)
    @PatchMapping("/{myPlantId}")
    public ResponseEntity<CommonResponse<Long>> deletePlant(@PathVariable("myPlantId") Long myPlantId) {
        Long plantId = myPlantUseCase.deletePlant(PassportHolder.getPassport().memberId(), myPlantId);
        return ResponseEntity.ok(new CommonResponse<>("내 식물이 삭제되었습니다.", plantId));
    }


    @Operation(summary = "내 식물 목록 조회", description = "내 식물을 목록으로 조회합니다.")
    @Authenticate(Role.MEMBER)
    @GetMapping("/list")
    public ResponseEntity<MyPlantListResponse> getMyPlantList() {
        List<MyPlantResponse> responseList = myPlantUseCase.getMyPlantList(PassportHolder.getPassport().memberId()).stream()
                .map(MyPlantResponse::from)
                .toList();
        return ResponseEntity.ok(MyPlantListResponse.of(responseList));
    }

    @Operation(summary = "내 식물 상세 조회", description = "내 식물을 상세 조회합니다.")
    @Authenticate(Role.MEMBER)
    @GetMapping("/detail")
    public ResponseEntity<MyPlantDetailResponse> getMyPlantByMyPlantId(@RequestParam("myPlantId") Long myPlantId) {
        MyPlant myPlant = myPlantUseCase.getMyPlantByMyPlantId(myPlantId);
        return ResponseEntity.ok(MyPlantDetailResponse.from(myPlant));
    }
}