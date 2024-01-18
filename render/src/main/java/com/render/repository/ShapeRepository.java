package com.render.repository;

import com.render.model.Shape;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ShapeRepository extends ReactiveCrudRepository<Shape, Integer> {
}
