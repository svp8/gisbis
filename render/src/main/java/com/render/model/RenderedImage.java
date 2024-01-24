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
    public void addLine(Geometry line) {
        Geometry box = bbox.getPolygon();
        if(!line.intersects(box)){
            return;
        }
        Set<Coordinate> points = new HashSet<>();
        Coordinate[] lineCoordinates = line.getCoordinates();
        Geometry intersections = box.getBoundary().intersection(line);
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
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.black);
        graphics.drawLine((int) imgPoints[0].getX(), (int) imgPoints[0].getY(), (int) imgPoints[1].getX(), (int) imgPoints[1].getY());

        for (Coordinate coor : points) {
            System.out.println("Intersects at " + coor);

        }
    }


public void addShape(Shape shape) {
    Graphics2D graphics = image.createGraphics();
    graphics.setStroke(new BasicStroke(1));
    graphics.setColor(new Color(shape.getR(), shape.getG(), shape.getB()));
    SecureRandom secureRandom = new SecureRandom();
//        int x = bbox.minPoint().x + secureRandom.nextInt(bbox.getWidth());
//        int y = bbox.minPoint().y + secureRandom.nextInt(bbox.getHeight());
//        int width = shape.getWidth();
//        if (shape.getWidth() + x >= bbox.maxPoint().x) {
//            width = bbox.maxPoint().x - x;
//        }
//        int height = shape.getHeight();
//        if (shape.getHeight() + y >= bbox.maxPoint().y) {
//            height = bbox.maxPoint().y - y;
//        }
//        graphics.fillRect(x, y, width, height);
}
}
