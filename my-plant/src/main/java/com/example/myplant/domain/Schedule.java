package com.example.myplant.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Getter
@Builder
public class Schedule {

    private Long scheduleId;

    private Long memberId;

    private String title;

    private Boolean isCompleted;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private Boolean isRecurring;

    private Integer frequency; // 반복 주기(일 기준)

    private String colorType;

    public boolean isWriter(Long originMemberId) {
        return originMemberId.equals(memberId);
    }


    // @TODO 현재 시간을 기준으로 일정이 과거인지 validation 체크 필요, 현재 테스트를 위해 열어둠
    public void checkEndDateTime() {
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

    public LocalDateTime getUpcomingDate() {
        LocalDateTime now = LocalDateTime.now();
        return now.plusDays(7);
    }

    public List<Schedule> findMatchingSchedules(List<Schedule> recurringScheduleList) {
        LocalDate today = LocalDate.now();

        return recurringScheduleList.stream()
                .filter(schedule -> isScheduleMatchingToday(schedule, today))
                .toList();
    }

    public List<Schedule> getTodayScheduleList(List<Schedule> scheduleList, List<Schedule> recurringScheduleList) {
        return Stream
                .concat(scheduleList.stream(), recurringScheduleList.stream())
                .toList();
    }

    private boolean isScheduleMatchingToday(Schedule schedule, LocalDate today) {
        LocalDateTime startDateTime = schedule.getStartDateTime();
        LocalDateTime endDateTime = schedule.getEndDateTime();
        int frequency = schedule.getFrequency();

        LocalDate currentDate = startDateTime.toLocalDate();
        while (!currentDate.isAfter(endDateTime.toLocalDate())) {
            if (currentDate.equals(today)) {
                return true;
            }
            currentDate = currentDate.plusDays(frequency);
        }
        return false;
    }
}
