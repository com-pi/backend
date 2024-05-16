package com.example.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class ExceptionHandlerFilter extends AbstractGatewayFilterFactory<ExceptionHandlerFilter.Config> {

    public ExceptionHandlerFilter() {
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(ExceptionHandlerFilter.Config config) {
        return new OrderedGatewayFilter(
                (exchange, chain) -> chain.filter(exchange)
                        .onErrorResume(e -> handleException(exchange, chain, e)),
                -50);

    }

    private Mono<Void> handleException(ServerWebExchange exchange, GatewayFilterChain chain, Throwable e) {
        // Todo gateway Exception 핸들링 필요

        final String authFailMessageJson = "{\"message\":\"Gateway Exception 발생.\"}";

        DataBuffer bodyBuffer = exchange.getResponse().bufferFactory().wrap(
                authFailMessageJson.getBytes(StandardCharsets.UTF_8));

        return exchange.getResponse()
                .writeWith(Mono.just(bodyBuffer))
                .then(Mono.fromRunnable(() -> {
                    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    exchange.getResponse().getHeaders().add("Content-Type", "application/json");
                }));

    }

    public static class Config {
    }

}
