package com.render;

import com.render.model.BBox;
import com.render.model.RenderedImage;
import com.render.model.Shape;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RendererTest {

    @Test
    void render() throws IOException {
        RenderedImage image=Renderer.createImage(100,100,new BBox(new Point(30,0),new Point(99,99)));


        Assertions.assertEquals(100,image.image().getWidth());
        Assertions.assertEquals(100,image.image().getHeight());
    }
}