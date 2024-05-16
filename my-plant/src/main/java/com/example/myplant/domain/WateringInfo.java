package com.example.myplant.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class WateringInfo {
    private int intervalInWeeks; // 물주기 주기 (주 단위)
    private int frequency; // 물주기 빈도 (회수)
}
