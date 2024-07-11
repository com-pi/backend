package com.example.myplant.adapter.out.persistence.entity;

import com.example.common.baseentity.DeletedAtAbstractEntity;
import com.example.myplant.domain.Schedule;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Builder
@Table(name = "schedule")
public class ScheduleEntity extends DeletedAtAbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    private Long memberId;

    private String title;

    private Boolean isCompleted;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private Boolean isRecurring;

    private Integer frequency; // 반복 주기(일 기준)

    private String colorType;

    public static ScheduleEntity from(Schedule schedule) {
        return ScheduleEntity.builder()
            .scheduleId(schedule.getScheduleId())
            .memberId(schedule.getMemberId())
            .title(schedule.getTitle())
            .isCompleted(schedule.getIsCompleted())
            .startDateTime(schedule.getStartDateTime())
            .endDateTime(schedule.getEndDateTime())
            .isRecurring(schedule.getIsRecurring())
            .frequency(schedule.getFrequency())
            .colorType(schedule.getColorType())
            .build();
    }

    public Schedule toDomain() {
        return Schedule.builder()
            .scheduleId(this.scheduleId)
            .memberId(this.memberId)
            .title(this.title)
            .isCompleted(this.isCompleted)
            .startDateTime(this.startDateTime)
            .endDateTime(this.endDateTime)
            .isRecurring(this.isRecurring)
            .frequency(this.frequency)
            .colorType(this.colorType)
            .build();
    }

    public void update(Schedule schedule) {
        this.title = schedule.getTitle();
        this.endDateTime = schedule.getEndDateTime();
    }

    public void updateStatus(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

}
