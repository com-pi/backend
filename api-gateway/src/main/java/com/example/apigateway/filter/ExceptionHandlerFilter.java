package com.example.apigateway.filter;

import com.example.apigateway.exception.InvalidAccessTokenException;
import com.example.apigateway.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class ExceptionHandlerFilter extends AbstractGatewayFilterFactory<ExceptionHandlerFilter.Config> {

    public ExceptionHandlerFilter() {
        super(Config.class);
    }

    private final String tokenReissueEndPoint = "/auth-service/token";
    // Todo 응답 반환 더 깔끔한 방법 없는지 확인 할 것
    private final String authFailMessageJson = "{\"message\":\"로그인이 필요합니다.\"}";

    @Override
    public GatewayFilter apply(ExceptionHandlerFilter.Config config) {
        return new OrderedGatewayFilter(
                (exchange, chain) -> chain.filter(exchange)
                .onErrorResume(e -> handleException(exchange, chain, e)),
                -50);

    }

    private Mono<Void> handleException(ServerWebExchange exchange, GatewayFilterChain chain, Throwable e) {
        if(e instanceof InvalidAccessTokenException) {
            if (CookieUtil.hasRefreshTokenCookie(exchange)) {
                String redirectUrl = UriComponentsBuilder.fromUri(exchange.getRequest().getURI())
                        .replacePath(tokenReissueEndPoint)
                        .build().toUriString();

                exchange.getResponse().setStatusCode(HttpStatus.FOUND);
                exchange.getResponse().getHeaders().setLocation(URI.create(redirectUrl));
                return exchange.getResponse().setComplete();
            }
        }
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
