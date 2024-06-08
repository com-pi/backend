package com.example.myplant.application.port.out;

import com.example.myplant.domain.Calendar;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoadCalendarPort {
    Optional<Calendar> loadCalendarById(Long id);
    List<Calendar> loadCalendarsByPlantId(Long plantId);
    List<Calendar> loadCalendarsByDate(LocalDate date);
}
