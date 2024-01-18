package com.render.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Shape {
    public Shape(int width, int height, int r, int g, int b) {
        this.width = width;
        this.height = height;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Id
    private int id;
    private int width;
    private int height;
    private int r;
    private int g;
    private int b;
}
