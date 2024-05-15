package com.example.apigateway.filter;

import com.example.apigateway.domain.Passport;
import com.example.apigateway.util.CookieUtil;
import com.example.apigateway.util.JwtValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;


@Slf4j
@Component
public class TokenValidateFilter extends AbstractGatewayFilterFactory<TokenValidateFilter.Config> {

    private final JwtValidator jwtValidator;

    public TokenValidateFilter(JwtValidator jwtValidator) {
        super(TokenValidateFilter.Config.class);
        this.jwtValidator = jwtValidator;
    }

    @Override
    public GatewayFilter apply(TokenValidateFilter.Config config) {
        return new OrderedGatewayFilter(
                (exchange, chain) -> {
                    Optional<String> accessTokenHeader = getAccessToken(exchange);

                    if(isApiDocRequest(exchange)) {
                        return chain.filter(exchange);
                    }

                    if(accessTokenHeader.isPresent()) {
                        Passport passport = jwtValidator.validateToken(accessTokenHeader.get());
                        return chain.filter(exchangeWithPassport(exchange, passport));
                    }

                    if(CookieUtil.hasRefreshTokenCookie(exchange)){
                        final String tokenReissueEndPoint = "/auth-service/token";

                        String redirectUrl = UriComponentsBuilder.fromUri(exchange.getRequest().getURI())
                                .replacePath(tokenReissueEndPoint)
                                .build().toUriString();

                        exchange.getResponse().setStatusCode(HttpStatus.SEE_OTHER);
                        exchange.getResponse().getHeaders().setLocation(URI.create(redirectUrl));
                        return exchange.getResponse().setComplete();
                    }

                    return chain.filter(exchange);
                },
                10);
    }

    private Boolean isApiDocRequest(ServerWebExchange exchange){
        String requestPath = exchange.getRequest().getPath().toString();
        return requestPath.endsWith("/v3/api-docs");
    }


    private Optional<String> getAccessToken(ServerWebExchange exchange){
        String authorization = exchange.getRequest().getHeaders().getFirst("Authorization");
        if(authorization != null && authorization.substring(0, 7).equalsIgnoreCase("Bearer ")){
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

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Config {
    }
}
