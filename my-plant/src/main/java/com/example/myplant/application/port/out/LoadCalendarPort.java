package com.example.myplant.application.port.out;

import com.example.myplant.domain.Calendar;

import java.util.List;
import java.util.Optional;

public interface LoadCalendarPort {
    Optional<Calendar> loadCalendarById(Long id);
    List<Calendar> loadAllCalendars();
}
