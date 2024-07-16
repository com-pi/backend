package com.example.encycloservice.adapter.out.persistence.entity;

import com.example.encycloservice.domain.PlantAddInquriy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantAddInquiryEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String commonName;
    private String scientificName;
    private Long requesterId;
    @Enumerated(EnumType.STRING)
    private PlantAddInquriy.Status status;
    private String result;

    public static PlantAddInquiryEntity fromDomain(PlantAddInquriy domain){
        return PlantAddInquiryEntity.builder()
                .commonName(domain.commonName())
                .scientificName(domain.scientificName())
                .requesterId(domain.requesterId())
                .status(domain.status())
                .result(domain.result())
                .build();
    }

}
