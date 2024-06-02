package com.example.authserver.util;

import com.example.common.domain.Location;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

public class GeomUtil {

    private final static GeometryFactory geometryFactory = new GeometryFactory();

    public static Point createPoint(Location location) {
        if(location == null){
            return null;
        }
        return geometryFactory.createPoint(
                new Coordinate(location.getLongitude(), location.getLatitude())
        );
    }

    public static Location createLocation(Point point) {
        if(point == null){
            return null;
        }
        return Location.of(point.getY(), point.getX());
    }

}
