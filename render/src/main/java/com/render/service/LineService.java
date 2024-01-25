package com.render.service;

import com.render.model.BBox;
import com.render.model.Line;
import com.render.repository.LineRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service
public class LineService {
    public static final int MAX_COLOR = 255;
    private final LineRepository lineRepository;

    @Autowired
    public LineService(LineRepository lineRepository) {
        this.lineRepository = lineRepository;
    }


    public Flux<Line> getAllInBBox(BBox bBox){
        return lineRepository.findAllInBBox(bBox);
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


    public Flux<Line> getList() {
        return lineRepository.findAll();
    }

}
