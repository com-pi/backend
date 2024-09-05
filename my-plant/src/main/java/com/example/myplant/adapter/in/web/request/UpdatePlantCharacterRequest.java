package com.example.myplant.adapter.in.web.request;

public record UpdatePlantCharacterRequest(
        Long plantCharacterId,
        String plantLocation
) {}
