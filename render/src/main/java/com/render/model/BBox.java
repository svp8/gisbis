package com.render.model;

import java.awt.*;

public record BBox(Point minPoint, Point maxPoint) {
    public int getWidth() {
        return maxPoint.x - minPoint.x;
    }

    public int getHeight() {
        return maxPoint.y - minPoint.y;
    }
}
