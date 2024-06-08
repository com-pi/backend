package com.example.myplant.adapter.in.web.command;

import com.example.common.baseentity.SelfValidating;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PlantCommand extends SelfValidating<PlantCommand> {

    @Schema(description = "Plant Name", example = "MyPlant")
    @NotNull
    private String plantName;

    @Schema(description = "Plant Type", example = "Flower")
    @NotNull
    private String plantType;

    @Schema(description = "Plant Age", example = "2")
    private int plantAge;

    @Schema(description = "Plant Birthday", example = "2022-01-01")
    @NotNull
    private LocalDate plantBirthday;

    @Schema(description = "Last Waterday", example = "2022-01-01")
    @NotNull
    private LocalDate lastWaterday;

    @Schema(description = "Watering Interval in Days", example = "7")
    private int wateringIntervalInDays;

    @Schema(description = "Plant Location", example = "Indoor")
    private String plantLocation;

    @Schema(description = "Pot Type", example = "Ceramic")
    private String potType;

    @Schema(description = "Character ID", example = "1")
    private Long characterId;

    @Builder
    public PlantCommand(String plantName, String plantType, int plantAge, LocalDate plantBirthday,
                        LocalDate lastWaterday, int wateringIntervalInDays,String plantLocation,
                        String potType, Long characterId) {
        this.plantName = plantName;
        this.plantType = plantType;
        this.plantAge = plantAge;
        this.plantBirthday = plantBirthday;
        this.lastWaterday = lastWaterday;
        this.wateringIntervalInDays = wateringIntervalInDays;
        this.plantLocation = plantLocation;
        this.potType = potType;
        this.characterId = characterId;
    }
}
