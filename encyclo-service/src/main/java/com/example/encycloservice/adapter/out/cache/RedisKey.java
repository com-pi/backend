package com.example.encycloservice.adapter.out.cache;

public class RedisKey {

    public static String recentPlantDetails = "recent_plant_detail";
    public static String popularity = "popularity";

    static String getPopularityPlantIdKey(String plantId) {
        return recentPlantDetails + plantId;
    }

    static String getPopularityPlantIdKey(Long plantId) {
        return recentPlantDetails + plantId;
    }

}
