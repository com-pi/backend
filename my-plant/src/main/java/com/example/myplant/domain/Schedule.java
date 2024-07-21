package com.example.myplant.domain;

import com.example.common.exception.ConflictException;
import com.example.common.exception.UnauthorizedException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Getter
@Builder
@AllArgsConstructor
public class Schedule implements Comparable<Schedule>{

    private Long scheduleId;

    private Long memberId;

    private String title;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private Boolean isRecurring;

    private Integer frequency; // 반복 주기(일 기준)

    private Boolean isCompleted;

    private String colorType;

    private boolean isWriter(Long originMemberId) {
        return originMemberId.equals(memberId);
    }


    // @TODO 현재 시간을 기준으로 일정이 과거인지 validation 체크 필요, 현재 테스트를 위해 열어둠
    public void validateEndDateTime() {
        if(endDateTime.isBefore(LocalDateTime.now())) {
            // throw new IllegalArgumentException("올바른 일정을 설정해주세요.");
        };
    }

    public void validateWriter(Schedule originSchedule, Schedule schedule) {
        if(!schedule.isWriter(originSchedule.getMemberId())) {
            throw new UnauthorizedException("일정을 수정할 권한이 없습니다.");
        }
    }

    public void validateScheduleCompletionDate(Schedule schedule, LocalDate date) {
        boolean isValidCompletionDate = schedule.getIsRecurring()
                ? schedule.isScheduleMatchingToday(schedule, date)
                : Objects.equals(schedule.getStartDateTime().toLocalDate(), date);

        if (!isValidCompletionDate) {
            throw new ConflictException("일정을 완료할 수 있는 일자가 아닙니다.");
        }
    }

    public String getDdayMessage(LocalDateTime now) {
        LocalDateTime dateTime = isRecurring ? startDateTime : endDateTime;
        Duration duration = Duration.between(now, dateTime);
        long days = duration.toDays();

        if(dateTime.isBefore(now)) {
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

    public List<Schedule> findMatchingSchedules(List<Schedule> recurringScheduleList, LocalDate today) {
        return recurringScheduleList.stream()
                .filter(schedule -> isScheduleMatchingToday(schedule, today))
                .map(schedule -> updateStartDate(schedule, today))
                .toList();
    }

    public List<Schedule> getMatchingScheduleList(List<Schedule> scheduleList, List<Schedule> recurringScheduleList) {
        return Stream
                .concat(scheduleList.stream(), recurringScheduleList.stream())
                .toList();
    }

    public boolean isScheduleMatchingToday(Schedule schedule, LocalDate today) {
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

    public List<LocalDate> getRecurringDate(Schedule schedule, LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = schedule.getStartDateTime();
        LocalDateTime endDateTime = schedule.getEndDateTime();
        int frequency = schedule.getFrequency();

        LocalDate currentDate = startDateTime.toLocalDate();
        List<LocalDate> recurringDateList = new ArrayList<>();

        while (!currentDate.isAfter(endDateTime.toLocalDate())) {
            if(!currentDate.isBefore(startDate) && !currentDate.isAfter(endDate)) {
                recurringDateList.add(currentDate);
            }

            currentDate = currentDate.plusDays(frequency);
            if(currentDate.isAfter(endDateTime.toLocalDate())) {
                break;
            }
        }
        return recurringDateList;
    }

    public List<Schedule> getRecurringSchedule(Schedule schedule, LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = schedule.getStartDateTime();
        LocalDateTime endDateTime = schedule.getEndDateTime();
        int frequency = schedule.getFrequency();

        LocalDate currentDate = startDateTime.toLocalDate();
        List<Schedule> recurringScheduleList = new ArrayList<>();

        while (!currentDate.isAfter(endDateTime.toLocalDate())) {
            if(!currentDate.isBefore(startDate) && !currentDate.isAfter(endDate)) {
                recurringScheduleList.add(updateStartDate(schedule, currentDate));
            }

            currentDate = currentDate.plusDays(frequency);
            if(currentDate.isAfter(endDateTime.toLocalDate())) {
                break;
            }
        }
        return recurringScheduleList;
    }

    @Override
    public int compareTo(Schedule o) {
        return this.startDateTime.compareTo(o.startDateTime);
    }

    public void setStatus(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    private Schedule updateStartDate(Schedule schedule, LocalDate currentDate) {
        return Schedule.builder()
                .scheduleId(schedule.getScheduleId())
                .memberId(schedule.getMemberId())
                .title(schedule.getTitle())
                .startDateTime(currentDate.atTime(schedule.startDateTime.toLocalTime()))
                .endDateTime(schedule.getEndDateTime())
                .isRecurring(schedule.getIsRecurring())
                .frequency(schedule.getFrequency())
                .colorType(schedule.getColorType())
                .build();
    }



}
