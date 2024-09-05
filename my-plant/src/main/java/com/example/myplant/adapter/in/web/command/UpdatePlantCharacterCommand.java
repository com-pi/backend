package com.example.myplant.adapter.in.web.command;

import com.example.myplant.adapter.in.web.request.UpdatePlantCharacterRequest;
import com.example.myplant.domain.MyPlant;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdatePlantCharacterCommand {
    private Long memberId;
    private Long myPlantId;
    private Long plantCharacterId;
    private String plantSpot;

    public static UpdatePlantCharacterCommand of(Long memberId, Long myPlantId, UpdatePlantCharacterRequest request) {
        return UpdatePlantCharacterCommand.builder()
                .memberId(memberId)
                .myPlantId(myPlantId)
                .plantCharacterId(request.plantCharacterId())
                .plantSpot(request.plantLocation())
                .build();
    }

    public MyPlant toDomain() {
        return MyPlant.builder()
                .memberId(memberId)
                .myPlantId(myPlantId)
                .plantCharacterId(plantCharacterId)
                .plantSpot(plantSpot)
                .build();
    }
}
