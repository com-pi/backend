package com.example.apigateway.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * RequestPath 를 수정하는 필터입니다.
 * prefix 정보를 받아서 Path 가 해당 prefix 를 가지면 prefix 를 제거합니다.
 * <p>
 * ex)
 * endpoint = "/board-service/article/3"
 * config.prefix = "/board-service"
 * 변경된 endpoint = "/article/3"
 */
@Component
@Slf4j
public class PathPrefixRemoveFilter extends AbstractGatewayFilterFactory<PathPrefixRemoveFilter.Config> {

    public PathPrefixRemoveFilter() {
        super(Config.class); // Config 클래스 타입을 생성자에 전달
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new OrderedGatewayFilter(
                (exchange, chain) -> {
                    String originalPath = exchange.getRequest().getPath().toString();
                    String modifiedPath = removePrefix(originalPath);

                    ServerWebExchange newExchange = exchange.mutate()
                            .request(exchange.getRequest().mutate().path(modifiedPath).build())
                            .build();
                    return chain.filter(newExchange);
                },
                100);
    }

    private String removePrefix(String originalPath) {
        String[] segments = originalPath.split("/");
        if (segments.length <= 2) {
            return "/";
        }
        return originalPath.replace("/" + segments[1], "");
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Config {
    }

}
