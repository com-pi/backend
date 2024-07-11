package com.example.myplant.domain;

import lombok.Getter;
public enum ScheduleColorType {

    RED("RED"), YELLOW("YELLOW"), LIGHT_GREEN("LIGHT_GREEN"), GREEN("GREEN"), LIGHT_BLUE(
            "LIGHT_BLUE"), BLUE("BLUE"), LIGHT_PURPLE("LIGHT_PURPLE"), PURPLE("PURPLE");

    @Getter
    public final String code;

    ScheduleColorType(final String code) {
        this.code = code;
    }

    public static ScheduleColorType from(String code) {
        for (ScheduleColorType type : ScheduleColorType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("일치하는 색상 코드가 없습니다. " + code);
    }
}
