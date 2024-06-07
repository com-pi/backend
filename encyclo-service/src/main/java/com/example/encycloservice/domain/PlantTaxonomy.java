package com.example.encycloservice.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Embeddable
public class PlantTaxonomy {
    // 종
    private String species;
    // 속
    private String genus;
    // 과
    private String family;
}
