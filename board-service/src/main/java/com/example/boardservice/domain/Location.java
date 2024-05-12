package com.example.boardservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Location {

    private double latitude;
    private double longitude;

    public static Location of(double latitude, double longitude) {
        return new Location(latitude, longitude);
    }

}
