package com.example.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public static Location of(Double latitude, Double longitude) {
        return new Location(latitude, longitude);
    }

    @JsonIgnore
    public boolean isNull(){
        return latitude == null && longitude == null;
    }

}
