package com.render.handler;

import com.render.Renderer;
import com.render.model.BBox;
import com.render.model.Point;
import com.render.model.RenderedImage;
import com.render.model.Shape;
import com.render.service.ShapeService;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class RenderHandler {
    private ShapeService shapeService;

    @Autowired
    public RenderHandler(ShapeService shapeService) {
        this.shapeService = shapeService;
    }

    public Mono<ServerResponse> render(ServerRequest request) {
        int width;
        int height;
        double minX;
        double minY;
        double maxX;
        double maxY;
        try {
            width = Integer.parseInt(request.queryParam("WIDTH").orElseThrow());
            height = Integer.parseInt(request.queryParam("HEIGHT").orElseThrow());
            String[] bbox = request.queryParam("BBOX").get().split(",");
            minX = Double.parseDouble(bbox[0]);
            minY = Double.parseDouble(bbox[1]);
            maxX = Double.parseDouble(bbox[2]);
            maxY = Double.parseDouble(bbox[3]);
        } catch (RuntimeException e) {
            return ServerResponse.badRequest().bodyValue("Some params does not exist or contain non integer value");
        }
//        if (!validateBBox(minX, minY, maxX, maxY, width, height)) {
//            return ServerResponse.badRequest().bodyValue("Invalid params");
//        }
        RenderedImage renderedImage = Renderer.createImage(width, height, new BBox(new Coordinate(minX, minY), new Coordinate(maxX, maxY)));
        List<Geometry> defaultLines = shapeService.getDefaultLines();
        for (Geometry defaultLine : defaultLines) {
            renderedImage.addLine(defaultLine);
        }
//        double boxHeight = renderedImage.bbox().getHeight();
//        double boxWidth = renderedImage.bbox().getWidth();
//        shapeService.save(shape).subscribe();
//        renderedImage.addShape(shape);
//        shapeService.save(shape).subscribe();
//        renderedImage.addShape(shape);

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ImageIO.write(renderedImage.image(), "png", os);
            Resource resource = new ByteArrayResource(os.toByteArray());
            return ServerResponse.ok().contentType(MediaType.IMAGE_PNG)
                    .body(BodyInserters.fromResource(resource));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean validateBBox(double minX, double minY, double maxX, double maxY, int width, int height) {
        if (minX < 0 || minY < 0 || maxX < 0 || maxY < 0) {
            return false;
        } else if (minX > maxX || minY > maxY) {
            return false;
        } else if (minX >= width || minY >= height || maxX >= width || maxY >= height) {
            return false;
        }
        return true;
    }
}
