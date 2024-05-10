package com.example.myplant.domain;



public enum PlantLocation {

    INSIDE, OUTSIDE;
// 실내, 실외 로만 우선 구분. 추후 주방 안방 베란다 등의 설정 추가 고려

    public static PlantLocation of(String location) {
        for(PlantLocation type : PlantLocation.values()){
            if(type.name().equalsIgnoreCase(location)) {
                return type;
            }
        }
        return null;
    }
}
