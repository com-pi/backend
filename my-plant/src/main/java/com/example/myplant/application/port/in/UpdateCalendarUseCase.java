package com.example.myplant.application.port.in;

import com.example.myplant.domain.Calendar;

public interface UpdateCalendarUseCase {
    Calendar updateCalendar(Long id, Calendar calendar);
}
