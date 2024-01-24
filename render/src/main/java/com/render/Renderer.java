package com.render;

import com.render.model.BBox;
import com.render.model.Point;
import com.render.model.RenderedImage;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Renderer {
    private final static double[] initialBounds = {-13884991, 2870341, -7455066, 6338219};

    public static RenderedImage createImage(int width, int height, BBox bbox) {
        double leftX = initialBounds[0];
        double leftY = initialBounds[1];
        double rightX = initialBounds[2];
        double rightY = initialBounds[3];
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        Coordinate coordinate=new Coordinate(leftX,leftY);
        Coordinate coordinate1=new Coordinate(rightX,rightY);
        Geometry line=new GeometryFactory().createLineString(new Coordinate[]{coordinate,coordinate1});
        Geometry box=new GeometryFactory().createPolygon(new Coordinate[]{new Coordinate(leftX,leftY),new Coordinate(leftX,rightY),new Coordinate(rightX,rightY),new Coordinate(rightX,leftY),new Coordinate(leftX,leftY)});
//        if(box.contains(line)){
//            Geometry  intersections = box.getBoundary().intersection(line);
//            Coordinate[] intersectionCoordinates=intersections.getCoordinates();
////            Coordinate point1=intersectionCoordinates[0];
////            Coordinate point2=intersectionCoordinates[1];
//            Coordinate[] imgPoints=new Coordinate[2];
//            for (int i = 0; i < intersectionCoordinates.length; i++) {
//                double xOffset = (intersectionCoordinates[i].getX()-bbox.minPoint().x()) / bbox.getWidth();
//                double xImg= width*xOffset;
//                double yOffset = (bbox.maxPoint().y() - intersectionCoordinates[i].getY()) / bbox.getHeight();
//                double yImg= height*yOffset;
//                imgPoints[i]=new Coordinate(xImg,yImg);
//            }
//            graphics.setColor(Color.green);
//            graphics.drawLine((int) imgPoints[0].getX(), (int) imgPoints[0].getY(), (int) imgPoints[1].getX(), (int) imgPoints[1].getY());
//
//            for(Coordinate coor : intersections.getCoordinates()){
//                System.out.println("Intersects at "+coor);
//
//            }
//        }
        return new RenderedImage(image, bbox);
    }

}
