package ru.aston.hometask.config;

import static org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions.circuitBreaker;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class GatewayConfig {

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return route("user-service")
                .route(path("/users")
                    .or(path("/users/**")), HandlerFunctions.http("http://user-service:8081"))
                .filter(circuitBreaker(c -> c
                        .setId("userServiceCircuitBreaker")
                        .setFallbackUri("forward:/fallback")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> fallbackRoute() {
        return route("fallback")
                .GET("/fallback", request ->
                        ServerResponse.status(503)
                                .body("Сервис не доступен, попробуйте позже"))
                .build();
    }
}
