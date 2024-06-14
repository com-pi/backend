package com.example.myplant.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long plantId;

    @Column(nullable = false)
    private String eventName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDate eventDate;

    @ManyToOne
    @JoinColumn(name = "plant_id", insertable = false, updatable = false)
    private Plant plant;

//    @OneToMany(mappedBy = "calendar")
//    private List<PlantDiary> plantDiaries;
}