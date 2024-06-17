package com.example.myplant.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Schedule {
    private Long scheduleId;
    private Long memberId;
    private String title;
    private LocalDateTime endDateTime;
    private Boolean isCompleted;

    public boolean isWriter(Long originMemberId) {
        return originMemberId.equals(memberId);
    }


    // @TODO 현재 시간을 기준으로 일정이 과거인지 validation 체크 필요, 현재 테스트를 위해 열어둠
    public void validateCheckEndDateTime() {
        if(endDateTime.isBefore(LocalDateTime.now())) {
            // throw new IllegalArgumentException("올바른 일정을 설정해주세요.");
        };
    }

    public String getEndDateTimeMessage(LocalDateTime now) {
        Duration duration = Duration.between(now, endDateTime);
        long days = duration.toDays();

        if(endDateTime.isBefore(now)) {
            return "D+" + days * -1;
        }

        if(days >= 1) {
            return "D-" + days;
        }

        return duration.toHours() + "시간 전";
    }
}
