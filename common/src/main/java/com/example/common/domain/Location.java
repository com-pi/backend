package com.example.common.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Location {
    private Double latitude;
    private Double longitude;

    public static Location of(Point point) {
        if(point == null) {
            return new Location(Double.NaN, Double.NaN);
        }
        return new Location(point.getX(), point.getY());
    }

}
