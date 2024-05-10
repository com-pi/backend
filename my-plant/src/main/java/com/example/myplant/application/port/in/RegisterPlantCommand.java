package com.example.myplant.application.port.in;

import com.example.myplant.common.SelfValidating;
import com.example.myplant.domain.PlantStatus;
import com.example.myplant.domain.PlantLocation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
@Builder(buildMethodName = "buildAndValidate")
public class RegisterPlantCommand extends SelfValidating<RegisterPlantCommand> {
    @NotNull @NotBlank private Long id;
    @NotNull @NotBlank private Long memberId;
    @NotNull @NotBlank private String plantName;
    @NotNull @Min(0) private String plantType;
    @NotNull private String plantAge;
    @NotNull private String plantBirthday;
    @NotNull private String plantImageUrl;
    @NotNull private String plantWaterDays;
    @NotNull private String lastWaterDay;
    @NotNull private String plantDescription;
    @NotNull private PlantLocation plantLocation;
    @NotNull private PlantStatus plantStatus;
    @NotNull private String intimacy;

    public RegisterPlantCommand(Long id,Long memberId, String plantName, String plantType,
                                String plantAge, String plantBirthday, String plantImageUrl,
                                String plantWaterDays, String lastWaterDay, String plantDescription,
                                PlantLocation plantLocation, PlantStatus plantStatus,String intimacy) {
        this.id = id;
        this.memberId = memberId;
        this.plantName = plantName;
        this.plantType = plantType;
        this.plantAge = plantAge;
        this.plantBirthday = plantBirthday;
        this.plantImageUrl = plantImageUrl;
        this.plantWaterDays = plantWaterDays;
        this.lastWaterDay = lastWaterDay;
        this.plantLocation = plantLocation;
        this.plantDescription = plantDescription;
        this.plantStatus = plantStatus;
        this.intimacy = intimacy;
        super.validateSelf();
    }

}

