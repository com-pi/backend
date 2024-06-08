package com.example.myplant.adapter.out.repository;

import com.example.myplant.domain.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    List<Calendar> findByPlantId(Long plantId);
    List<Calendar> findByEventDate(LocalDate eventDate);
}
