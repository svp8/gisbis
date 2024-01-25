package com.render.handler;

import com.render.Renderer;
import com.render.model.BBox;
import com.render.model.RenderedImage;
import com.render.service.LineService;
import org.locationtech.jts.geom.Coordinate;
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

@Component
public class RenderHandler {

    private final LineService lineService;

    @Autowired
    public RenderHandler(LineService lineService) {
        this.lineService = lineService;
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
        RenderedImage renderedImage = Renderer.createImage(width, height, new BBox(new Coordinate(minX, minY), new Coordinate(maxX, maxY)));
        Mono<Resource> result= lineService.getAllInBBox(renderedImage.bbox()).doOnNext(renderedImage::addLine).then(Mono.just(renderedImage)).map(x->{
            try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                ImageIO.write(renderedImage.image(), "png", os);
                Resource resource = new ByteArrayResource(os.toByteArray());
                return resource;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return ServerResponse.ok().contentType(MediaType.IMAGE_PNG)
                    .body(BodyInserters.fromProducer(result,Resource.class));
    }

}
