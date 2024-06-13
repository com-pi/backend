package com.example.myplant.adapter.in.web.response;

import java.util.List;

public record MyPlantListResponse(
        List<MyPlantResponse> myPlantResponseList
) {
    public static MyPlantListResponse of(List<MyPlantResponse> myPlantResponseList) {
        return new MyPlantListResponse(myPlantResponseList);
    }
}
