package com.example.myplant.event;

import com.example.myplant.application.service.ScheduleService;
import com.example.myplant.domain.Schedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
@Slf4j
public class MyPlantEventListener {

    private final ScheduleService scheduleService;

    @TransactionalEventListener
    @Async
    public void handleEvent(CommonEvent commonEvent) {
        if(commonEvent instanceof Schedule) {
            scheduleService.createSchedule((Schedule) commonEvent);
        }
    }

}
