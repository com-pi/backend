package com.example.myplant.adapter.out.persistence;

import com.example.myplant.adapter.out.persistence.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    List<ScheduleEntity> findByMemberIdAndIsCompletedOrderByEndDateTime(Long memberId, Boolean isCompleted);

    @Query("""
        SELECT s 
        FROM ScheduleEntity s 
        WHERE DATE(s.startDateTime) = CURRENT_DATE
            AND s.memberId = :memberId
            AND s.isCompleted = :isCompleted
            AND s.frequency IS NULL
    """)
    List<ScheduleEntity> getTodayScheduleList(
            @Param("memberId") Long memberId,
            @Param("isCompleted") boolean isCompleted
    );

    @Query("""
        SELECT s 
        FROM ScheduleEntity s 
        WHERE CURRENT_DATE >= DATE(s.startDateTime)
            AND CURRENT_DATE <= DATE(s.endDateTime)
            AND s.memberId = :memberId
            AND s.isCompleted = :isCompleted
            AND s.frequency IS NOT NULL
    """)
    List<ScheduleEntity> getRecurringScheduleList(Long memberId, boolean isCompleted);


    @Query("""
        SELECT s
        FROM ScheduleEntity s 
        WHERE DATE(s.endDateTime) > CURRENT_DATE
            AND DATE(s.endDateTime) <= DATE(:endDate)
            AND s.memberId = :memberId
            AND s.isCompleted = :isCompleted
   """)
    List<ScheduleEntity> getUpcomingScheduleList(
            @Param("endDate") LocalDateTime endDate,
            @Param("memberId") Long memberId,
            @Param("isCompleted") boolean isCompleted
    );
}
