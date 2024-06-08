package com.example.myplant.adapter.out.persistence;

import com.example.myplant.adapter.out.repository.CalendarRepository;
import com.example.myplant.application.port.out.LoadCalendarPort;
import com.example.myplant.application.port.out.SaveCalendarPort;
import com.example.myplant.domain.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class CalendarPersistenceAdapter implements SaveCalendarPort, LoadCalendarPort {

    private final CalendarRepository calendarRepository;

    @Autowired
    public CalendarPersistenceAdapter(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    @Override
    public Calendar saveCalendar(Calendar calendar) {
        return calendarRepository.save(calendar);
    }

    @Override
    public void deleteCalendar(Calendar calendar) {
        calendarRepository.delete(calendar);
    }

    @Override
    public Optional<Calendar> loadCalendarById(Long id) {
        return calendarRepository.findById(id);
    }

    @Override
    public List<Calendar> loadCalendarsByPlantId(Long plantId) {
        return calendarRepository.findByPlantId(plantId);
    }

    @Override
    public List<Calendar> loadCalendarsByDate(LocalDate date) {
        return calendarRepository.findByEventDate(date);
    }
}
