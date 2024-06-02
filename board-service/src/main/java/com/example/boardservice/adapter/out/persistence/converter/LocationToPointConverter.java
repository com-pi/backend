package com.example.boardservice.adapter.out.persistence.converter;

import com.example.boardservice.domain.Location;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

@Converter
@RequiredArgsConstructor
public class LocationToPointConverter implements AttributeConverter<com.example.boardservice.domain.Location, Point> {

    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    @Override
    public Point convertToDatabaseColumn(Location location) {
        return geometryFactory.createPoint(new Coordinate(127.01, 38.04));
    }

    @Override
    public Location convertToEntityAttribute(Point point) {
        if (point == null) {
            return null;
        }
        return Location.of(point.getY(), point.getX());
    }
}
