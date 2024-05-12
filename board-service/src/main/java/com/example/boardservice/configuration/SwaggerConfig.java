package com.example.boardservice.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    @Profile("local")
    public OpenAPI localApi() {
        SecurityScheme securityScheme = new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .info(new Info().title("꼼삐 Swagger").description("식물생활 서비스 API"))
                .components(new Components().addSecuritySchemes("bearer", securityScheme))
                .security(Collections.singletonList(securityRequirement))
                .addServersItem(new Server().url("/board-service"));
    }

    @Bean
    @Profile("dev")
    public OpenAPI devApi() {
        SecurityScheme securityScheme = new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                .info(new Info().title("꼼삐 Swagger").description("식물생활 서비스 API"))
                .components(new Components().addSecuritySchemes("bearer", securityScheme))
                .security(Collections.singletonList(securityRequirement))
                .addServersItem(new Server().url("https://api.com-p.site/board-service/"));
    }
}
