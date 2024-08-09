package com.example.apigateway.filter;

import com.example.apigateway.exception.ExceptionResponse;
import com.example.apigateway.exception.InvalidTokenException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
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

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ExceptionHandlerFilter() {
        super(Config.class);
    }
    @Override
    public GatewayFilter apply(ExceptionHandlerFilter.Config config) {
        return new OrderedGatewayFilter(
                (exchange, chain) -> chain.filter(exchange)
                        .onErrorResume(e -> handleException(exchange, e)),
                -50);
    }

    private Mono<Void> handleException(ServerWebExchange exchange, Throwable e) {
        ExceptionResponse responseBody;

        if (e instanceof InvalidTokenException invalidTokenException) {
            responseBody = ExceptionResponse.of(InvalidTokenException.STATUS, invalidTokenException.getMessage());
        } else {
            responseBody = ExceptionResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }

        return writeResponse(exchange, responseBody, e);
    }

    private Mono<Void> writeResponse(ServerWebExchange exchange, ExceptionResponse responseBody, Throwable e) {
        String responseBodyJson;

        try {
            responseBodyJson = objectMapper.writeValueAsString(responseBody);
        } catch (JsonProcessingException ex) {
            responseBodyJson = e.getMessage();
        }

        DataBuffer bodyBuffer = exchange.getResponse().bufferFactory().wrap(responseBodyJson.getBytes(StandardCharsets.UTF_8));
        exchange.getResponse().setStatusCode(HttpStatus.resolve(responseBody.getStatus()));
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");

        log.info("Exception 발생", e);

        return exchange.getResponse().writeWith(Mono.just(bodyBuffer));
    }

    public static class Config {
    }

}
