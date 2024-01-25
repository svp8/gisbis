package com.render.converter;

import com.render.model.Line;
import io.r2dbc.spi.Row;
import lombok.NonNull;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ReadingConverter
public class GeometryReadConverter implements Converter<String, Geometry> {
    @Override
    public Geometry convert(@NonNull String row) {
        Pattern pattern = Pattern.compile("LINESTRING\\((.* .*),(.* .*)\\)");
        Matcher matcher = pattern.matcher(row);
        if (matcher.find()) {
            String[] points1 = matcher.group(1).split(" ");
            Coordinate cor1 = new Coordinate(Double.parseDouble(points1[0]), Double.parseDouble(points1[1]));
            String[] points2 = matcher.group(2).split(" ");
            Coordinate cor2 = new Coordinate(Double.parseDouble(points2[0]), Double.parseDouble(points2[1]));
            return new GeometryFactory().createLineString(new Coordinate[]{cor1, cor2});
        } else {
            System.out.println("NO MATCH");
        }
        return null;
    }
}
