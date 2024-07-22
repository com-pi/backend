package com.example.myplant.event;

import com.example.myplant.domain.Schedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MyPlantEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publishPlantSchedule(Schedule schedule) {
        eventPublisher.publishEvent(schedule);
    }
}
