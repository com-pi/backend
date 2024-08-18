package com.example.myplant.adapter.in.web.command;

import com.example.myplant.domain.MyPlant;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdatePlantCharacterCommand {
    private Long memberId;
    private Long myPlantId;
    private Long plantCharacterId;

    public static UpdatePlantCharacterCommand of(Long memberId, Long myPlantId, Long plantCharacterId) {
        return UpdatePlantCharacterCommand.builder()
                .memberId(memberId)
                .myPlantId(myPlantId)
                .plantCharacterId(plantCharacterId)
                .build();
    }

    public MyPlant toDomain() {
        return MyPlant.builder()
                .memberId(memberId)
                .myPlantId(myPlantId)
                .plantCharacterId(plantCharacterId)
                .build();
    }
}
