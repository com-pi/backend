package com.example.myplant.domain;

import com.example.common.exception.UnauthorizedException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPlant {

    private Long myPlantId;

    private Long memberId;

    private String plantName;

    private String plantType;

    private LocalDate plantBirthday;

    private LocalDate lastWateringDate;

    private Integer wateringIntervalInDays;

    private String plantSpot;

    private String potType;

    private Integer relationshipScore;

    private String deletionYn;

    private Long plantCharacterId;

    public void validatePermission(MyPlant originMyPlant) {
        if(!isWriter(originMyPlant.getMemberId())) {
            throw new UnauthorizedException("캐릭터를 수정할 권한이 없습니다.");
        }
    }

    private boolean isWriter(Long originMemberId) {
        return originMemberId.equals(memberId);
    }
}
