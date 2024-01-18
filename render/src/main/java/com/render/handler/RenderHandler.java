package com.render.handler;

import com.render.Renderer;
import com.render.model.BBox;
import com.render.model.RenderedImage;
import com.render.model.Shape;
import com.render.service.ShapeService;
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
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
        int minX;
        int minY;
        int maxX;
        int maxY;
        try {
            width = Integer.parseInt(request.queryParam("width").orElseThrow());
            height = Integer.parseInt(request.queryParam("height").orElseThrow());
            minX = Integer.parseInt(request.queryParam("minX").orElse("0"));
            minY = Integer.parseInt(request.queryParam("minY").orElse("0"));
            maxX = Integer.parseInt(request.queryParam("maxX").orElseThrow());
            maxY = Integer.parseInt(request.queryParam("maxY").orElseThrow());
        } catch (RuntimeException e) {
            return ServerResponse.badRequest().bodyValue("Some params does not exist or contain non integer value");
        }
        if (!validateBBox(minX, minY, maxX, maxY, width, height)) {
            return ServerResponse.badRequest().bodyValue("Invalid params");
        }
        RenderedImage renderedImage = Renderer.createImage(width, height, new BBox(new Point(minX, minY), new Point(maxX, maxY)));
        int boxHeight = renderedImage.bbox().getHeight();
        int boxWidth = renderedImage.bbox().getWidth();
        Shape shape = shapeService.createRandom(boxHeight, boxWidth);
        shapeService.save(shape).subscribe();
        renderedImage.addShape(shape);
        shape = shapeService.createRandom(boxHeight, boxWidth);
        shapeService.save(shape).subscribe();
        renderedImage.addShape(shape);

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ImageIO.write(renderedImage.image(), "png", os);
            Resource resource = new ByteArrayResource(os.toByteArray());
            return ServerResponse.ok().contentType(MediaType.IMAGE_PNG)
                    .body(BodyInserters.fromResource(resource));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean validateBBox(int minX, int minY, int maxX, int maxY, int width, int height) {
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
