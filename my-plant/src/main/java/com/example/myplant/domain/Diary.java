package com.example.myplant.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long plantId;
    private String title;
    private String content;

    @ElementCollection
    private List<String> managementTypes;

    private LocalDate date;

    @ElementCollection
    private List<String> images;

    private boolean isPublic;

}