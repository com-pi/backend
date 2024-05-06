package com.example.apigateway.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> implements Ordered {

    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // Todo: 요청 로깅

            return chain.filter(exchange);
//                        .then(Mono.fromRunnable(() -> {
//                            // Todo: 응답로깅
//                        })
        };


    }
    @Override
    public int getOrder() {
        return -20;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class Config {

    }

}
