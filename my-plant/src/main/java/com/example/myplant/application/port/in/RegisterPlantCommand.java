package com.example.myplant.application.port.in;

import com.example.myplant.common.SelfValidating;
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
    @NotNull @NotBlank private Long memberId;
    @NotNull @NotBlank private String plantName;
    @NotNull @Min(0) private String plantType;
    @NotNull private String plantAge;
    @NotNull private String plantBirthday;
    @NotNull private String plantImageUrl;
    @NotNull private String plantWaterDays;
    @NotNull private String lastWaterDay;
    @NotNull private String plantDescription;
    @NotNull private String status;
    @NotNull private String intimacy;

    public RegisterPlantCommand(Long memberId, String plantName, String plantType,
                                String plantAge, String plantBirthday, String plantImageUrl,
                                String plantWaterDays, String lastWaterDay, String plantDescription,
                                String status, String intimacy) {
        this.memberId = memberId;
        this.plantName = plantName;
        this.plantType = plantType;
        this.plantAge = plantAge;
        this.plantBirthday = plantBirthday;
        this.plantImageUrl = plantImageUrl;
        this.plantWaterDays = plantWaterDays;
        this.lastWaterDay = lastWaterDay;
        this.plantDescription = plantDescription;
        this.status = status;
        this.intimacy = intimacy;
        super.validateSelf();
    }

}

