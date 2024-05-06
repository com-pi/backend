package com.example.apigateway.filter;

import com.example.apigateway.exception.NoAccessTokenException;
import com.example.apigateway.util.JwtValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.example.apigateway.util.JwtValidator.TokenType.ACCESS_TOKEN;


@Slf4j
@Component
public class TokenValidateFilter extends AbstractGatewayFilterFactory<TokenValidateFilter.Config> implements Ordered {

    private final JwtValidator jwtValidator;

    public TokenValidateFilter(JwtValidator jwtValidator) {
        super(TokenValidateFilter.Config.class);
        this.jwtValidator = jwtValidator;
    }

    @Override
    public GatewayFilter apply(TokenValidateFilter.Config config) {
        return (exchange, chain) ->
                hasAuthorizationHeader(exchange)
                .filter(hasHeader -> hasHeader)

                .then(getToken(exchange))
                .flatMap(token -> jwtValidator.validateTokenReactive(token, ACCESS_TOKEN))
                .switchIfEmpty(Mono.error(new NoAccessTokenException()))

                .flatMap(passport -> {
                    ServerHttpRequest requestWithPassport = exchange.getRequest().mutate()
                            .headers(httpHeaders -> httpHeaders.add("passport", passport.toJson()))
                            .build();
                    return chain.filter(exchange.mutate().request(requestWithPassport).build());
                });
    }

    private Mono<Boolean> hasAuthorizationHeader(ServerWebExchange exchange) {
        List<String> authorizationHeader = exchange.getRequest().getHeaders().get("Authorization");
        return Mono.just(
                authorizationHeader != null &&
                        authorizationHeader.size() == 1 &&
                        authorizationHeader.get(0).startsWith("Bearer ")
        );
    }

    private Mono<String> getToken(ServerWebExchange exchange) {
        return Mono.just(exchange.getRequest().getHeaders().get("Authorization")
                .get(0).substring(7)); // 널체크 되어있음 정적 분석 도구 오류
    }

    @Override
    public int getOrder() {
        return 10;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    public static class Config {
        // Filter 에 Configuration 정보를 전달 합니다.
        private String prefix;
    }
}
