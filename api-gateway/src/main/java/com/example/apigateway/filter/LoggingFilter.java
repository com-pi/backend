package com.example.apigateway.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Objects;

@Slf4j
@Component
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new OrderedGatewayFilter(
                (exchange, chain) -> {
                    logRequest(exchange);

                    return chain.filter(exchange)
                            .then(Mono.fromRunnable(() -> logResponse(exchange)));
                }, -100);
    }

    private void logRequest(ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        HttpMethod method = exchange.getRequest().getMethod();
        URI uri = exchange.getRequest().getURI();

        Logger logger = LoggerFactory.getLogger(GatewayFilter.class);
        logger.info("Request {} {} Headers: {}", method, uri, headers);
    }

    private void logResponse(ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getResponse().getHeaders();
        HttpStatusCode statusCode = exchange.getResponse().getStatusCode();

        Logger logger = LoggerFactory.getLogger(GatewayFilter.class);
        logger.info("Response status {} Headers: {}", Objects.requireNonNull(statusCode).value(), headers);
    }


    @AllArgsConstructor
    @Getter
    @Setter
    public static class Config {

    }

}
