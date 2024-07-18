package com.example.authserver.adapter.in;

import com.example.authserver.adapter.in.request.LocationRequest;
import com.example.authserver.application.port.in.CommonUseCase;
import com.example.common.baseentity.CommonResponse;
import com.example.common.domain.Address;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "기타", description = "주소 변환")
public class CommonController {

    private final CommonUseCase commonUseCase;

    @GetMapping("/address")
    @Operation(summary = "주소 변환 api", description = "위도, 경도를 입력하면 주소를 반환합니다.")
    public ResponseEntity<CommonResponse<Address>> getAddress(
            @Parameter(description = "위도, 33 ~ 38")
            @RequestParam(value = "latitude", required = false) String lat,
            @Parameter(description = "경도, 125 ~ 132")
            @RequestParam(value = "longitude", required = false) String lon
    ){

        Address address = commonUseCase.getAddress(
                LocationRequest.builder()
                        .latitude(lat)
                        .longitude(lon)
                        .build().toDomain()
        );

        return CommonResponse.okWithMessage("주소 정보를 조회하였습니다.", address);
    }

}
