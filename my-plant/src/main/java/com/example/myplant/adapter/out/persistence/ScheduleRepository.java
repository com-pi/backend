package com.example.myplant.adapter.out.persistence;

import com.example.myplant.adapter.out.persistence.entity.ScheduleEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    List<ScheduleEntity> findByMemberIdAndIsCompletedOrderByEndDateTime(Long memberId, Boolean isCompleted);

}
