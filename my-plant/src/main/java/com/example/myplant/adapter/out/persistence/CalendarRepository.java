package com.example.myplant.adapter.out.persistence;

import com.example.myplant.domain.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
}
