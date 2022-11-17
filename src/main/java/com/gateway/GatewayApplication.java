package com.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.factory.TokenRelayGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
    @Autowired
    private TokenRelayGatewayFilterFactory filterFactory;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                .route(r -> r
                        .path("/api/**")
                        .filters(f -> f.filters(filterFactory.apply())
                                .removeRequestHeader("Cookie"))
                        .uri("http://localhost:4000/api"))
                .route(r -> r
                        .path("/**")
                        .filters(f -> f.filters(filterFactory.apply())
                                .removeRequestHeader("Cookie"))
                        .uri("http://localhost:3000"))
                .build();
    }

}
