package com.example.authserver.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class SwaggerConfig {

    @Bean
    @Profile("local")
    public OpenAPI localApi() {
        return new OpenAPI()
                .info(new Info().title("꼼삐 Swagger").description("인증/인가 서비스 API"))
                .addServersItem(new Server().url("/auth-service"));
    }

    @Bean
    @Profile("dev")
    public OpenAPI devApi() {
        return new OpenAPI()
                .info(new Info().title("꼼삐 Swagger").description("인증/인가 서비스 API"))
                .addServersItem(new Server().url("https://api.com-p.site/auth-service/"));
    }

}