package com.example.common.domain;

import lombok.*;
import org.locationtech.jts.geom.Point;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@EqualsAndHashCode
public class Location {

    private Double latitude;
    private Double longitude;

    public static Location of(Point point) {
        if(point == null) {
            return new Location(Double.NaN, Double.NaN);
        }
        return new Location(point.getX(), point.getY());
    }

    public static Location of(double latitude, double longitude) {
        return new Location(latitude, longitude);
    }

}
