package com.example.authserver.adapter.in.request;

import com.example.common.domain.AddressValue;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record ModifyLocationRequest(
        @Parameter(name = "위도")
        @Min(value = 33, message = "대한민국 위도 한계를 벗어났습니다. (33 ~ 38")
        @Max(value = 38, message = "대한민국 위도 한계를 벗어났습니다. (33 ~ 38")
        Double latitude,
        @Parameter(name = "경도")
        @Min(value = 125, message = "대한민국 경도 한계를 벗어났습니다. (33 ~ 38")
        @Max(value = 132, message = "대한민국 경도 한계를 벗어났습니다. (33 ~ 38")
        Double longitude,
        @Valid
        @Parameter(name = "주소")
        AddressValue address
        ) { }
