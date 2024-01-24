package com.render.service;

import com.render.model.Shape;
import com.render.repository.ShapeRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShapeService {
    public static final int MAX_COLOR = 255;
    private ShapeRepository shapeRepository;

    @Autowired
    public ShapeService(ShapeRepository shapeRepository) {
        this.shapeRepository = shapeRepository;
    }

    public Mono<Shape> save(Shape shape) {
        return shapeRepository.save(shape);
    }

    public Shape createRandom(double boxHeight, double boxWidth) {
        SecureRandom secureRandom = new SecureRandom();
        int r = secureRandom.nextInt(MAX_COLOR);
        int g = secureRandom.nextInt(MAX_COLOR);
        int b = secureRandom.nextInt(MAX_COLOR);
        double height = secureRandom.nextDouble(boxHeight);
        double width = secureRandom.nextDouble(boxWidth);
        return new Shape(width, height, r, g, b);
    }

    public List<Geometry> getDefaultLines() {//возвращает линии которые образуют HI
        GeometryFactory geometryFactory = new GeometryFactory();
        List<Geometry> lines = new ArrayList<>();

        Geometry line = new GeometryFactory().createLineString(new Coordinate[]{new Coordinate(-13213210.457489, 6001468.652791), new Coordinate(-13213210.457489, 4499633.921044)});
        lines.add(line);
        line = new GeometryFactory().createLineString(new Coordinate[]{new Coordinate(-13213210.457489, 5001468.652791), new Coordinate(-12348310.254753, 5001468.652791)});
        lines.add(line);
        line = new GeometryFactory().createLineString(new Coordinate[]{new Coordinate(-12348310.254753, 6001468.652791),
                new Coordinate(-12348310.254753, 4499633.921044)});
        lines.add(line);

        line = new GeometryFactory().createLineString(new Coordinate[]{new Coordinate(-12200572.706767, 6001468.652791),
                new Coordinate(-11864005.243538, 6001468.652791)});
        lines.add(line);
        line = new GeometryFactory().createLineString(new Coordinate[]{new Coordinate(-12000572.706767, 6001468.652791),
                new Coordinate(-12000572.706767, 4499633.921044)});
        lines.add(line);
        line = new GeometryFactory().createLineString(new Coordinate[]{new Coordinate(-12200572.706767, 4499633.921044),
                new Coordinate(-11864005.243538, 4499633.921044)});
        lines.add(line);
        return lines;
    }


    public Flux<Shape> getList() {
        return shapeRepository.findAll();
    }

    public Mono<Shape> getById(int id) {
        return shapeRepository.findById(id);
    }
}
