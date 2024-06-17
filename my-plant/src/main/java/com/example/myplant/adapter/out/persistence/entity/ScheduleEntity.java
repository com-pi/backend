package com.example.myplant.adapter.out.persistence.entity;

import com.example.common.baseentity.DeletedAtAbstractEntity;
import com.example.myplant.domain.Schedule;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private LocalDateTime endDateTime;

    public static ScheduleEntity from(Schedule schedule) {
        return ScheduleEntity.builder()
            .scheduleId(schedule.getScheduleId())
            .memberId(schedule.getMemberId())
            .title(schedule.getTitle())
            .isCompleted(schedule.getIsCompleted())
            .endDateTime(schedule.getEndDateTime())
            .build();
    }

    public Schedule toDomain() {
        return Schedule.builder()
            .scheduleId(this.scheduleId)
            .memberId(this.memberId)
            .title(this.title)
            .isCompleted(this.isCompleted)
            .endDateTime(this.endDateTime)
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
