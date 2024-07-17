package com.example.encycloservice.adapter.out.persistence.entity;

import com.example.common.baseentity.BaseTimeAbstractEntity;
import com.example.common.baseentity.DeletedAtAbstractEntity;
import com.example.encycloservice.domain.PlantAddInquiry;
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
@Table(name = "PLANT_ADD_INQUIRY")
public class PlantAddInquiryEntity extends DeletedAtAbstractEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String commonName;
    private String scientificName;
    private Long requesterId;
    @Enumerated(EnumType.STRING)
    private PlantAddInquiry.Status status;
    private String result;

    public static PlantAddInquiryEntity fromDomain(PlantAddInquiry domain){
        return PlantAddInquiryEntity.builder()
                .commonName(domain.commonName())
                .scientificName(domain.scientificName())
                .requesterId(domain.requesterId())
                .status(domain.status())
                .result(domain.result())
                .build();
    }

    public static PlantAddInquiry toDomain(PlantAddInquiryEntity entity) {
        return PlantAddInquiry.builder()
                .id(entity.id)
                .commonName(entity.commonName)
                .scientificName(entity.scientificName)
                .requesterId(entity.requesterId)
                .status(entity.status)
                .result(entity.result)
                .build();
    }

    public void update(PlantAddInquiry domain) {
        this.status = domain.status();
        this.result = domain.result();
    }

}
