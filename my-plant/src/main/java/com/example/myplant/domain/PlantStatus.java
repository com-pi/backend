package com.example.myplant.domain;

public enum PlantStatus {
    Dead, Alive;

    public static PlantStatus of(String status) {
        for(PlantStatus type : PlantStatus.values()){
            if(type.name().equalsIgnoreCase(status)) {
                return type;
            }
        }
        return null;
    }
}
