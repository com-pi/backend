package com.example.authserver.adapter.in.request;

import com.example.common.baseentity.SelfValidating;
import com.example.common.domain.Location;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class LocationRequest extends SelfValidating<LocationRequest> {

    @Min(value = 33, message = "대한민국 위도 한계를 벗어났습니다. (33 ~ 38")
    @Max(value = 38, message = "대한민국 위도 한계를 벗어났습니다. (33 ~ 38")
    private final Double latitude;

    @Min(value = 125, message = "대한민국 경도 한계를 벗어났습니다. (33 ~ 38")
    @Max(value = 132, message = "대한민국 경도 한계를 벗어났습니다. (33 ~ 38")
    private final Double longitude;

    @Builder
    public LocationRequest(String latitude, String longitude) {
        this.latitude = Double.valueOf(latitude);
        this.longitude = Double.valueOf(longitude);
        super.validateSelf();
    }

    public Location toDomain() {
        return Location.of(latitude, longitude);
    }

}
