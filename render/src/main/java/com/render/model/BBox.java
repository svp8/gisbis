package com.render.model;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;

import java.awt.*;

public record BBox(Coordinate minPoint, Coordinate maxPoint) {
    public double getWidth() {
        return maxPoint.getX() - minPoint.getX();
    }

    public double getHeight() {
        return maxPoint.getY() - minPoint.getY();
    }

    public Geometry getPolygon() {
        double leftX = minPoint().getX();
        double leftY = minPoint().getY();
        double rightX = maxPoint().getX();
        double rightY = maxPoint().getY();
        return new GeometryFactory().createPolygon(new Coordinate[]{new Coordinate(leftX, leftY), new Coordinate(leftX, rightY), new Coordinate(rightX, rightY), new Coordinate(rightX, leftY), new Coordinate(leftX, leftY)});
    }

}
