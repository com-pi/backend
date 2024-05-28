package com.example.myplant.application.port.out;

import com.example.myplant.domain.Calendar;

public interface SaveCalendarPort {
    Calendar saveCalendar(Calendar calendar);
}
