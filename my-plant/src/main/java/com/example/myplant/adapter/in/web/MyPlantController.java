package com.example.myplant.adapter.in.web;

import com.example.myplant.application.port.in.RegistPlantUseCase;
import com.example.myplant.application.port.in.UpdatePlantUseCase;
import com.example.myplant.adapter.in.web.PlantCommand;
import com.example.myplant.adapter.in.web.UpdatePlantCommand;
import com.example.myplant.domain.Plant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/my-plant")
@Tag(name = "식물 관리", description = "식물 등록 및 수정 API")
public class MyPlantController {

    private final RegistPlantUseCase registPlantUseCase;
    private final UpdatePlantUseCase updatePlantUseCase;

    @Autowired
    public MyPlantController(RegistPlantUseCase registPlantUseCase, UpdatePlantUseCase updatePlantUseCase) {
        this.registPlantUseCase = registPlantUseCase;
        this.updatePlantUseCase = updatePlantUseCase;
    }

    @Operation(summary = "식물 등록", description = "새로운 식물을 등록합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = PlantCommand.class))
            ))
    @PostMapping
    public ResponseEntity<Plant> createPlant(@Valid @RequestBody PlantCommand command) {
        Plant plant = registPlantUseCase.registPlant(command);
        return ResponseEntity.ok(plant);
    }

    @Operation(summary = "식물 수정", description = "기존 식물의 정보를 수정합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = UpdatePlantCommand.class))
            ))
    @PutMapping("/{id}")
    public ResponseEntity<Plant> updatePlant(@PathVariable Long id, @Valid @RequestBody UpdatePlantCommand command) {
        command.setId(id);
        Plant updatedPlant = updatePlantUseCase.updatePlant(command);
        return ResponseEntity.ok(updatedPlant);
    }
}




