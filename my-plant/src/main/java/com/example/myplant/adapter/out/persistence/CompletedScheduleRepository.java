package com.example.myplant.adapter.out.persistence;

import com.example.myplant.adapter.out.persistence.entity.CompletedScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CompletedScheduleRepository extends JpaRepository<CompletedScheduleEntity, Long> {

    Optional<CompletedScheduleEntity> findBySchedule_ScheduleIdAndCompletedDate(Long scheduleId, LocalDate now);

    List<CompletedScheduleEntity> findBySchedule_ScheduleIdInAndCompletedDate(List<Long> scheduleIdList, LocalDate date);
}
