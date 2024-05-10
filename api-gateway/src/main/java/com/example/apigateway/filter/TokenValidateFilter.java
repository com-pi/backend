package com.example.apigateway.filter;

import com.example.apigateway.domain.Passport;
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

import java.util.Optional;


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
        return (exchange, chain) -> {

            if(isFromSwagger(exchange)) {
                return chain.filter(exchange);
            }

            String accessToken = getAccessToken(exchange).orElseThrow(NoAccessTokenException::new);
            Passport passport = jwtValidator.validateToken(accessToken)
                    .orElseThrow(NoAccessTokenException::new);
            return chain.filter(exchangeWithPassport(exchange, passport));
        };
    }

    private Boolean isFromSwagger(ServerWebExchange exchange){
        String endPoint = exchange.getRequest().getURI().getPath();
        String requestPath = exchange.getRequest().getPath().toString();
        boolean isApiDocRequest = endPoint.endsWith("/v3/api-docs");
        boolean isFromSwagger = requestPath.contains("swagger");
        return isApiDocRequest || isFromSwagger;
    }


    private Optional<String> getAccessToken(ServerWebExchange exchange){
        String authorization = exchange.getRequest().getHeaders().getFirst("Authorization");
        if(authorization != null && authorization.startsWith("Bearer ")){
            return Optional.of(authorization.substring(7));
        }
        return Optional.empty();
    }

    private ServerWebExchange exchangeWithPassport(ServerWebExchange exchange, Passport passport){
        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                .headers(httpHeaders -> httpHeaders.add("passport", passport.toJson()))
                .build();
        return exchange.mutate().request(modifiedRequest).build();
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
