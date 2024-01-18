package com.render.model;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;


public record RenderedImage(BufferedImage image, BBox bbox) {
    public void addShape(Shape shape) {
        Graphics2D graphics = image.createGraphics();
        graphics.setStroke(new BasicStroke(1));
        graphics.setColor(new Color(shape.getR(), shape.getG(), shape.getB()));
        SecureRandom secureRandom = new SecureRandom();
        int x = bbox.minPoint().x + secureRandom.nextInt(bbox.getWidth());
        int y = bbox.minPoint().y + secureRandom.nextInt(bbox.getHeight());
        int width = shape.getWidth();
        if (shape.getWidth() + x >= bbox.maxPoint().x) {
            width = bbox.maxPoint().x - x;
        }
        int height = shape.getHeight();
        if (shape.getHeight() + y >= bbox.maxPoint().y) {
            height = bbox.maxPoint().y - y;
        }
        graphics.fillRect(x, y, width, height);
    }
}
