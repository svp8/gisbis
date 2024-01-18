package com.render.service;

import com.render.model.Shape;
import com.render.repository.ShapeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;

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

    public Shape createRandom(int boxHeight, int boxWidth) {
        SecureRandom secureRandom = new SecureRandom();
        int r = secureRandom.nextInt(MAX_COLOR);
        int g = secureRandom.nextInt(MAX_COLOR);
        int b = secureRandom.nextInt(MAX_COLOR);
        int height = secureRandom.nextInt(boxHeight);
        int width = secureRandom.nextInt(boxWidth);
        return new Shape(width, height, r, g, b);
    }


    public Flux<Shape> getList() {
        return shapeRepository.findAll();
    }

    public Mono<Shape> getById(int id) {
        return shapeRepository.findById(id);
    }
}
