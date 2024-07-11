package com.example.myplant.adapter.out.persistence;

import com.example.myplant.adapter.out.persistence.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    List<ScheduleEntity> findByMemberIdAndIsCompletedOrderByEndDateTime(Long memberId, Boolean isCompleted);

    @Query("""
        SELECT s 
        FROM ScheduleEntity s 
        WHERE DATE(s.startDateTime) = CURRENT_DATE
    """)
    List<ScheduleEntity> getTodayScheduleList(Long memberId, boolean isCompleted);

}
