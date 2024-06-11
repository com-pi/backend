package com.example.myplant.application.port.in;

import com.example.myplant.adapter.in.web.command.CalendarCommand;
import com.example.myplant.domain.Calendar;

import java.time.LocalDate;
import java.util.List;

public interface CalendarUseCase {
    Long createCalendar(CalendarCommand command);
    void updateCalendar(Long id, CalendarCommand command);
    Calendar getCalendarById(Long id);
    List<Calendar> getCalendarsByPlantId(Long plantId);
    List<Calendar> getCalendarsByDate(LocalDate date);
}
