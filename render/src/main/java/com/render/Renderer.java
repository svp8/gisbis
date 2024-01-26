package com.render;

import com.render.model.BBox;
import com.render.model.RenderedImage;

import java.awt.image.BufferedImage;

public class Renderer {

    public static RenderedImage createImage(int width, int height, BBox bbox) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        return new RenderedImage(image, bbox);
    }

}
