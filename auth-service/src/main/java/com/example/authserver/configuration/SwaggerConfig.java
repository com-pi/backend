package com.example.authserver.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SwaggerConfig {

    private final String docsUrl;

    public SwaggerConfig(Environment environment) {
        String[] activeProfiles = environment.getActiveProfiles();
        if(Arrays.asList(activeProfiles).contains("local")) {
            docsUrl = "/auth-service";
        } else {
            docsUrl = "https://api.com-p.site/auth-service/";
        }
    }

    @Bean
    public OpenAPI localApi() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .info(new Info().title("꼼삐 Swagger").description("인증/인가 서비스 API"))
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                .security(Collections.singletonList(securityRequirement))
                .addServersItem(new Server().url(docsUrl));
    }

}