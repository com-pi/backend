package com.example.imagemodule.util;

import com.example.imagemodule.domain.MinioBucket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
public class ObjectUrlMapperConfig {

    private final String prefix;

    public ObjectUrlMapperConfig(Environment env) {
        String activeProfile;
        String[] activeProfiles = env.getActiveProfiles();
        if (activeProfiles.length == 0) {
            activeProfile = "dev";
        } else {
            activeProfile = activeProfiles[0];
        }
        if (activeProfile.equals("local")) {
            prefix = "http://localhost:9000";
        } else {
            prefix = "https://bucket.com-p.site";
        }
    }

    @Bean
    public ObjectUrlMapper objectUrlMapperDev() {
        return new ObjectUrlMapper() {
            @Override
            public String toObject(String url) {
                return url.replace(prefix, "");
            }

            @Override
            public String toUrl(String object, MinioBucket bucket) {
                return String.format("%s/%s/%s", prefix, bucket.getBucketName(), object);
            }

            @Override
            public List<String> toUrl(List<String> objectNames, MinioBucket bucket) {
                return objectNames.stream()
                        .map(objectName -> this.toUrl(objectName, bucket))
                        .toList();
            }
        };
    }

}
