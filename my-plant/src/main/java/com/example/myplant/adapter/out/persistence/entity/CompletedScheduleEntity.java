package com.example.myplant.adapter.out.persistence.entity;

import com.example.myplant.domain.CompletedSchedule;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Builder
@Table(name = "completed_schedule", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"schedule_id", "completed_date"})
})
public class CompletedScheduleEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long completedScheduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private ScheduleEntity schedule;

    private LocalDate completedDate;

    public static CompletedScheduleEntity of(ScheduleEntity scheduleEntity, LocalDate completedDate) {
        return CompletedScheduleEntity.builder()
                .schedule(scheduleEntity)
                .completedDate(completedDate)
                .build();
    }

    public CompletedSchedule toDomain() {
        return CompletedSchedule.builder()
                .completedScheduleId(this.completedScheduleId)
                .scheduleId(this.schedule.getScheduleId())
                .completedDate(this.completedDate)
                .build();
    }

}
