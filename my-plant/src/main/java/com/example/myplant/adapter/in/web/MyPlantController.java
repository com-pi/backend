package com.example.myplant.adapter.in.web;

import com.example.myplant.application.PostPlantService;
import com.example.myplant.application.port.in.RegisterPlantCommand;
import com.example.myplant.application.port.in.PostPlantUseCase;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPlantController {

    @Autowired
    private final PostPlantUseCase postPlantUseCase;

    @Tag(name = "식물 등록", description = "새로운 식물을 등록합니다.")
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void registerPlant(
        @Parameter(description = "회원ID", required = true) Long member_id,
        @Parameter(description = "식물명", required = true) String plant_name,
        @Parameter(description = "식물 종류", required = true) String plant_type,
        @Parameter(description = "식물 나이", required = true) String plant_age,
        @Parameter(description = "식물 생일", required = true) String plant_birthday,
        @Parameter(description = "식물 사진 Url", required = true) String plant_image_url,
        @Parameter(description = "식물 급수 주기", required = true) String plant_water_days,
        @Parameter(description = "급수 날짜", required = true) String last_water_day,
        @Parameter(description = "식물 설명", required = true) String plant_description,
        @Parameter(description = "상태", required = true) String status,
        @Parameter(description = "친밀도", required = true) String intimacy)
    {
        RegisterPlantCommand request = RegisterPlantCommand.builder()
                .memberId(member_id)
                .plantName(plant_name)
                .plantType(plant_type)
                .plantAge(plant_age)
                .plantBirthday(plant_birthday)
                .plantImageUrl(plant_image_url)
                .plantWaterDays(plant_water_days)
                .lastWaterDay(last_water_day)
                .plantDescription(plant_description)
                .status(status)
                .intimacy(intimacy)
                .buildAndValidate();
        postPlantUseCase.postRegisterPlant(request);
    }
}







