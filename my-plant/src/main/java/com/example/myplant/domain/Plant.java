package com.example.myplant.domain;

import com.example.myplant.adapter.out.persistence.JsonToStringListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "my_plant")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "generate")

public class Plant {


    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Plant(Long id) {
        this.id = id;
    }

    private Long memberId;

    private String plantName;
    private String plantType;
    private String plantAge;
    private String plantBirthday;

    @Convert(converter = JsonToStringListConverter.class)
    private List<String> plantImageUrl;


    private String plantWaterDays;
    private String lastWaterDay;
    private String intimacy;
    private String plantDescription;

    @Enumerated(EnumType.STRING)
    private PlantLocation plantLocation;

    @Enumerated(EnumType.STRING)
    private PlantStatus plantStatus;



    public static Plant ofId(Long authorId){
        return new Plant(authorId);
    }

}
