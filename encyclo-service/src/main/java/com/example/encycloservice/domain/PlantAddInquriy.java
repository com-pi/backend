package com.example.encycloservice.domain;


import lombok.Builder;

@Builder
public record PlantAddInquriy(

        Long id,
        String commonName,
        String scientificName,
        Long requesterId,
        Status status,
        String result

) {

    public enum Status {
        SUBMITTED,
        IN_PROGRESS,
        PROCESSED
    }

}
