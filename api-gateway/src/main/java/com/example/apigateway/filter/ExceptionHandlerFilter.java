package com.example.apigateway.filter;

import com.example.apigateway.exception.NoAccessTokenException;
import com.example.apigateway.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class ExceptionHandlerFilter extends AbstractGatewayFilterFactory<ExceptionHandlerFilter.Config> implements Ordered {

    public ExceptionHandlerFilter() {
        super(Config.class);
    }

    private final String tokenReissueEndPoint = "/auth-service/token";
    private final String authFailMessageJson = "{\"message\":\"로그인이 필요합니다.\"}";

    @Override
    public GatewayFilter apply(ExceptionHandlerFilter.Config config) {
        return (exchange, chain) -> {

            try {
                return chain.filter(exchange);
            }

            catch (NoAccessTokenException exception) {

                if (CookieUtil.hasRefreshTokenCookie(exchange)) {
                    ServerHttpRequest tokenReissueRequest = exchange.getRequest().mutate()
                            .path(tokenReissueEndPoint)
                            .method(HttpMethod.GET)
                            .build();
                    return chain.filter(exchange.mutate().request(tokenReissueRequest).build());
                }

                else {
                    DataBuffer bodyBuffer = exchange.getResponse().bufferFactory().wrap(
                            authFailMessageJson.getBytes(StandardCharsets.UTF_8));

                    return exchange.getResponse()
                            .writeWith(Mono.just(bodyBuffer))
                            .then(Mono.fromRunnable(() -> {
                                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                                exchange.getResponse().getHeaders().add("Content-Type", "application/json");
                            }));
                }

            }
        };

    }


    @Override
    public int getOrder() {
        return -10;
    }

    public static class Config {

    }
}
