package com.render.router;

import com.render.handler.RenderHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class RenderRouter {
    @Bean
    public RouterFunction<ServerResponse> route(RenderHandler renderHandler) {

        return RouterFunctions
                .route(GET("/create").and(accept(MediaType.APPLICATION_JSON)), renderHandler::render);
    }
}
