package com.example.boardservice.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
public class ObjectUrlMapperConfig {

    public final String devPrefix= "https://bucket.com-p.site/";
    public final String localPrefix= "http://localhost:9000/";

    @Bean
    @Profile("dev")
    public ObjectUrlMapper objectUrlMapperDev() {
        return new ObjectUrlMapper() {
            @Override
            public String toObject(String url) {
                return url.replace(devPrefix, "");
            }

            @Override
            public String toUrl(String object) {
                return devPrefix + object;
            }

            @Override
            public List<String> toUrl(List<String> objectNames) {
                return objectNames.stream().map(this::toUrl).toList();            }
        };
    }

    @Bean
    @Profile("local")
    public ObjectUrlMapper objectUrlMapperLocal() {
        return new ObjectUrlMapper() {
            @Override
            public String toObject(String url) {
                return url.replace(localPrefix, "");
            }

            @Override
            public String toUrl(String object) {
                return localPrefix + object;
            }

            @Override
            public List<String> toUrl(List<String> objectNames) {
                return objectNames.stream().map(this::toUrl).toList();
            }
        };
    }

}
