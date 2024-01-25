package com.render.repository;

import com.render.model.BBox;
import com.render.model.Line;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
@Repository
public interface LineRepository extends ReactiveCrudRepository<Line, Integer> {
    @Query(" Select id,ST_AsText(geom) as geometry,color from entity e where st_intersects(" +
            "ST_MakeEnvelope(:#{#box.minPoint().getX()}, :#{#box.minPoint().getY()}, :#{#box.maxPoint().getX()}, :#{#box.maxPoint().getY()}, 3857)" +
            ",e.geom);")
    Flux<Line> findAllInBBox(@Param("box") BBox box);
}
