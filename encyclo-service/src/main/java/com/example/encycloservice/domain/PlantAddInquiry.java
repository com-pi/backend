package com.example.encycloservice.domain;


import lombok.Builder;

@Builder(toBuilder = true)
public record PlantAddInquiry(

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

    public PlantAddInquiry process(PlantAddInquiryProcess process){
        return this.toBuilder()
                .status(process.status())
                .result(process.result())
                .build();
    }

}
