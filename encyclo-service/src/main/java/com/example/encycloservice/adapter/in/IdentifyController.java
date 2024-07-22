package com.example.encycloservice.adapter.in;

import com.example.common.annotation.Authenticate;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.Role;
import com.example.encycloservice.adapter.in.response.PlantIdentifyResponse;
import com.example.encycloservice.application.port.in.IdentifyPlantUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "식물 인식", description = "주어진 사진을 기반으로 식물을 인식합니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/identify")
public class IdentifyController {

    private final IdentifyPlantUseCase identifyPlantUseCase;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Authenticate(Role.MEMBER)
    public ResponseEntity<CommonResponse<PlantIdentifyResponse>> identify(
            @RequestPart List<MultipartFile> images
    ) {
        PlantIdentifyResponse plantIdentifyResponse = identifyPlantUseCase.identifyPlant(images);
        return CommonResponse.okWithMessage("식물 인식 성공", plantIdentifyResponse);
    }

}
