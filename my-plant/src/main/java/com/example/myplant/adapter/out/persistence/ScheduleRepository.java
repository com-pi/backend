package com.example.myplant.adapter.out.persistence;

import com.example.myplant.adapter.out.persistence.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    List<ScheduleEntity> findByMemberIdAndIsCompletedOrderByEndDateTime(Long memberId, Boolean isCompleted);

    @Query("""
        SELECT s
        FROM ScheduleEntity s
        WHERE FUNCTION('DATE', s.startDateTime) = CURRENT_DATE
            AND s.memberId = :memberId
            AND s.isCompleted = :isCompleted
            AND s.isRecurring = false
    """)
    List<ScheduleEntity> getTodayScheduleList(
            @Param("memberId") Long memberId,
            @Param("isCompleted") boolean isCompleted
    );

    @Query("""
        SELECT s
        FROM ScheduleEntity s
        WHERE CURRENT_DATE >= FUNCTION('DATE', s.startDateTime)
            AND CURRENT_DATE <= FUNCTION('DATE', s.endDateTime)
            AND s.memberId = :memberId
            AND s.isCompleted = :isCompleted
            AND s.isRecurring = true
    """)
    List<ScheduleEntity> getRecurringScheduleList(Long memberId, boolean isCompleted);

    @Query("""
        SELECT s FROM ScheduleEntity s
        WHERE s.memberId = :memberId
        AND s.isRecurring = true
        AND s.deletionYn = 'N'
        AND
        (
          (:startDate >= FUNCTION('DATE', s.startDateTime) AND :startDate <= FUNCTION('DATE', s.endDateTime)) OR
          (:endDate >= FUNCTION('DATE', s.startDateTime) AND :endDate <= FUNCTION('DATE', s.endDateTime)) OR
          (FUNCTION('DATE', s.startDateTime) >= :startDate AND FUNCTION('DATE', s.startDateTime) <= :endDate) OR
          (FUNCTION('DATE', s.endDateTime) >= :startDate AND FUNCTION('DATE', s.endDateTime) <= :endDate)
        )
    """)
    List<ScheduleEntity> getUpcomingRecurringScheduleList(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("memberId") Long memberId
    );

    @Query("""
        SELECT s FROM ScheduleEntity s
        WHERE s.memberId = :memberId
        AND s.isRecurring = false
        AND s.deletionYn = 'N'
        AND FUNCTION('DATE', s.startDateTime) > :startDate
        AND FUNCTION('DATE', s.startDateTime) <= :endDate
    """)
    List<ScheduleEntity> getUpcomingScheduleList(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("memberId") Long memberId
    );

    @Query("""
        SELECT s FROM ScheduleEntity s
        WHERE s.memberId = :memberId
        AND s.isRecurring = true
        AND s.deletionYn = 'N'
        AND
        (
          (:startDate >= FUNCTION('DATE', s.startDateTime) AND :startDate <= FUNCTION('DATE', s.endDateTime)) OR
          (:endDate >= FUNCTION('DATE', s.startDateTime) AND :endDate <= FUNCTION('DATE', s.endDateTime)) OR
          (FUNCTION('DATE', s.startDateTime) >= :startDate AND FUNCTION('DATE', s.startDateTime) <= :endDate) OR
          (FUNCTION('DATE', s.endDateTime) >= :startDate AND FUNCTION('DATE', s.endDateTime) <= :endDate)
        )
    """)
    List<ScheduleEntity> getRecurringScheduleCalendarList(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("memberId") Long memberId
    );

    @Query("""
        SELECT s FROM ScheduleEntity s
        WHERE s.memberId = :memberId
        AND s.isRecurring = false
        AND s.deletionYn = 'N'
        AND FUNCTION('DATE', s.startDateTime) >= :startDate
        AND FUNCTION('DATE', s.startDateTime) <= :endDate
    """)
    List<ScheduleEntity> getScheduleCalendarList(
            @Param("startDate") LocalDate localDate,
            @Param("endDate") LocalDate endDate,
            @Param("memberId") Long memberId
    );

    @Query("""
        SELECT s FROM ScheduleEntity s
        WHERE s.memberId = :memberId
        AND s.isRecurring = false
        AND s.deletionYn = 'N'
        AND FUNCTION('DATE', s.startDateTime) = :date
    """)
    List<ScheduleEntity> getSingleScheduleCalender(
            @Param("date") LocalDate date,
            @Param("memberId") Long memberId
    );


}
