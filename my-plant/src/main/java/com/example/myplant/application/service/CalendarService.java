package com.example.myplant.application.service;

import com.example.myplant.adapter.in.web.command.CalendarCommand;
import com.example.myplant.application.port.in.CalendarUseCase;
import com.example.myplant.application.port.out.LoadCalendarPort;
import com.example.myplant.application.port.out.SaveCalendarPort;
import com.example.myplant.domain.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CalendarService implements CalendarUseCase {
    private final SaveCalendarPort saveCalendarPort;
    private final LoadCalendarPort loadCalendarPort;

    @Autowired
    public CalendarService(SaveCalendarPort saveCalendarPort, LoadCalendarPort loadCalendarPort) {
        this.saveCalendarPort = saveCalendarPort;
        this.loadCalendarPort = loadCalendarPort;
    }

    @Override
    public Long createCalendar(CalendarCommand command) {
        Calendar calendar = Calendar.builder()
                .plantId(command.getPlantId())
                .eventName(command.getEventName())
                .description(command.getDescription())
                .eventDate(command.getEventDate())
                .build();
        return saveCalendarPort.saveCalendar(calendar).getId();
    }

    @Override
    public void updateCalendar(Long id, CalendarCommand command) {
        Optional<Calendar> optionalCalendar = loadCalendarPort.loadCalendarById(id);
        if (optionalCalendar.isPresent()) {
            Calendar calendar = optionalCalendar.get();
            calendar.setEventName(command.getEventName());
            calendar.setDescription(command.getDescription());
            calendar.setEventDate(command.getEventDate());
            saveCalendarPort.saveCalendar(calendar);
        } else {
            throw new RuntimeException("Calendar not found");
        }
    }

    @Override
    public Calendar getCalendarById(Long id) {
        return loadCalendarPort.loadCalendarById(id)
                .orElseThrow(() -> new RuntimeException("Calendar not found"));
    }

    @Override
    public List<Calendar> getCalendarsByPlantId(Long plantId) {
        return loadCalendarPort.loadCalendarsByPlantId(plantId);
    }

    @Override
    public List<Calendar> getCalendarsByDate(LocalDate date) {
        return loadCalendarPort.loadCalendarsByDate(date);
    }
}