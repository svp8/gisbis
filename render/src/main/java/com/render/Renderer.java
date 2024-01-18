package com.render;

import com.render.model.BBox;
import com.render.model.RenderedImage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Renderer {
    public static RenderedImage createImage(int width, int height, BBox bbox) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, width, height);
        graphics.setStroke(new BasicStroke(1));
        graphics.setColor(Color.BLACK);
        graphics.drawRect(bbox.minPoint().x, bbox.minPoint().y, bbox.getWidth(), bbox.getHeight());
        return new RenderedImage(image, bbox);
    }

}
