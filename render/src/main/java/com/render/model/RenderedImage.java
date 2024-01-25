package com.render.model;


import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.*;
import java.util.List;


public record RenderedImage(BufferedImage image, BBox bbox) {
    public void addLine(Line line) {
        Geometry box = bbox.getPolygon();
        Geometry lineGeometry= line.getGeometry();
//        if(!lineGeometry.intersects(box)){
//            return;
//        }
        Set<Coordinate> points = new HashSet<>();
        Coordinate[] lineCoordinates = lineGeometry.getCoordinates();
        Geometry intersections = box.getBoundary().intersection(lineGeometry);
        Coordinate[] intersectionCoordinates = intersections.getCoordinates();
        if (intersectionCoordinates.length != 0) {
            points.addAll(Arrays.asList(intersectionCoordinates));
        }
        if (points.size() < 2) {
            GeometryFactory geometryFactory = new GeometryFactory();
            for (Coordinate lineCoordinate : lineCoordinates) {
                if (box.contains(geometryFactory.createPoint(lineCoordinate))) {
                    points.add(lineCoordinate);
                }
            }
        }

        Coordinate[] imgPoints=new Coordinate[2];
        int i=0;
        for (Coordinate point : points) {
            double xOffset = (point.getX() - bbox.minPoint().getX()) / bbox.getWidth();
            double xImg = image.getWidth() * xOffset;
            double yOffset = (bbox.maxPoint().getY() - point.getY()) / bbox.getHeight();
            double yImg = image.getHeight() * yOffset;
            imgPoints[i++] = new Coordinate(xImg, yImg);
        }

        synchronized (image){
            Graphics2D graphics = image.createGraphics();
            graphics.setColor(Color.decode(line.getColor()));
            graphics.drawLine((int) imgPoints[0].getX(), (int) imgPoints[0].getY(), (int) imgPoints[1].getX(), (int) imgPoints[1].getY());
        }


    }
}
