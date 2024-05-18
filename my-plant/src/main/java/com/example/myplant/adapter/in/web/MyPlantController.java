package com.example.myplant.adapter.in.web;

import com.example.common.annotation.Authenticate;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.Role;
import com.example.myplant.application.port.in.RegistPlantUseCase;
import com.example.myplant.application.port.in.UpdatePlantUseCase;
import com.example.myplant.application.port.out.FindPlantPort;
import com.example.myplant.domain.Plant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController

public class MyPlantController {

    private final RegistPlantUseCase registPlantUseCase;
    private final UpdatePlantUseCase updatePlantUseCase;
    private final ObjectMapper objectMapper;
    private final FindPlantPort findPlantPort;

    @Autowired
    public MyPlantController(RegistPlantUseCase registPlantUseCase, UpdatePlantUseCase updatePlantUseCase, FindPlantPort findPlantPort, ObjectMapper objectMapper) {
        this.registPlantUseCase = registPlantUseCase;
        this.updatePlantUseCase = updatePlantUseCase;
        this.objectMapper = objectMapper;
        this.findPlantPort = findPlantPort;
    }
    @Tag(name = "식물 관리", description = "식물 등록 및 관리를 위한 API")
    @Authenticate(Role.MEMBER)
    @PostMapping(value = "/regist-plant", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "식물 등록", description = "새로운 식물을 등록합니다.")
    public ResponseEntity<CommonResponse<Long>> registPlant(
            @Schema(description = "식물 정보 JSON",
                    example = "{\"memberId\":1,\"plantName\":\"Rose\",\"plantType\":\"Flower\"," +
                            "\"plantAge\":2,\"plantBirthday\":\"2021-01-01\",\"wateringIntervalInWeeks\":2," +
                            "\"wateringFrequency\":3,\"repottingDate\":\"2022-01-01\",\"fertilizingDate\":\"2022-01-15\"," +
                            "\"pruningDate\":\"2022-02-01\",\"plantLocation\":\"Indoor\",\"potType\":\"Plastic\"}"
            )
            @RequestPart("plantData") String plantData,
            @RequestPart("plantImages") List<MultipartFile> plantImages) throws JsonProcessingException{

        PlantCommand command;
        command = objectMapper.readValue(plantData, PlantCommand.class);
        command.setIntimacy(1);
        command.setPlantImages(plantImages);

        Plant registeredPlant = registPlantUseCase.registerPlant(command);
        return ResponseEntity.ok(new CommonResponse<>("Plant registered successfully",registeredPlant.getId()));

    }
    @GetMapping("/{plantId}")
    @Operation(summary = "식물 정보 조회", description = "ID로 식물 정보를 조회합니다.")
    public ResponseEntity<CommonResponse<Plant>> getPlant(@PathVariable Long plantId) {
        Plant plant = findPlantPort.findPlantById(plantId).orElseThrow(() -> new RuntimeException("Plant not found"));
        return ResponseEntity.ok(new CommonResponse<>("Plant retrieved successfully",plant));
    }

    @PutMapping(value = "/update-plant/{plantId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "식물 정보 수정", description = "기존 식물의 정보를 수정합니다.")
    public ResponseEntity<CommonResponse<Long>> updatePlant(
            @PathVariable Long plantId,
            @Schema(description = "식물 정보 JSON",
                    example = "{\"memberId\":1,\"plantName\":\"Rose\",\"plantType\":\"Flower\"," +
                            "\"plantAge\":2,\"plantBirthday\":\"2021-01-01\",\"wateringIntervalInWeeks\":2," +
                            "\"wateringFrequency\":3,\"repottingDate\":\"2022-01-01\",\"fertilizingDate\":\"2022-01-15\"," +
                            "\"pruningDate\":\"2022-02-01\",\"plantLocation\":\"Indoor\",\"potType\":\"Plastic\"}"
            )
            @RequestPart("plantData") String plantData,
            @RequestPart("plantImages") List<MultipartFile> plantImages) throws JsonProcessingException{

        UpdatePlantCommand command;
        command = objectMapper.readValue(plantData, UpdatePlantCommand.class);
        command.setPlantImages(plantImages);

        Plant updatedPlant = updatePlantUseCase.updatePlant(plantId, command);
        return ResponseEntity.ok(new CommonResponse<>("Plant updated successfully",updatedPlant.getId()));
    }
}