package com.example.myplant.application.service;

import com.example.myplant.domain.Calendar;
import com.example.myplant.application.port.in.CreateCalendarUseCase;
import com.example.myplant.application.port.in.UpdateCalendarUseCase;
import com.example.myplant.application.port.out.LoadCalendarPort;
import com.example.myplant.application.port.out.SaveCalendarPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarService implements CreateCalendarUseCase, UpdateCalendarUseCase {
    private final SaveCalendarPort saveCalendarPort;
    private final LoadCalendarPort loadCalendarPort;

    @Autowired
    public CalendarService(SaveCalendarPort saveCalendarPort, LoadCalendarPort loadCalendarPort) {
        this.saveCalendarPort = saveCalendarPort;
        this.loadCalendarPort = loadCalendarPort;
    }

    @Override
    public Calendar createCalendar(Calendar calendar) {
        return saveCalendarPort.saveCalendar(calendar);
    }

    @Override
    public Calendar updateCalendar(Long id, Calendar calendar) {
        Calendar existingCalendar = loadCalendarPort.loadCalendarById(id)
                .orElseThrow(() -> new RuntimeException("Calendar not found"));
        existingCalendar.setTitle(calendar.getTitle());
        existingCalendar.setDescription(calendar.getDescription());
        existingCalendar.setPlant(calendar.getPlant());
        return saveCalendarPort.saveCalendar(existingCalendar);
    }

    public List<Calendar> getAllCalendars() {
        return loadCalendarPort.loadAllCalendars();
    }
}