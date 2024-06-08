package com.example.myplant.adapter.in.web.command;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarCommand {
    private Long plantId;
    private String eventName;
    private String description;
    private LocalDate eventDate; // 이벤트 날짜 추가
}
