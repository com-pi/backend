package com.example.boardservice.common.config;

import com.example.boardservice.domain.ArticleStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
public class DeserializerConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(articleStatusTypeConverter());
    }

    @Bean
    public Converter<String, ArticleStatusType> articleStatusTypeConverter() {
        return source -> {
            try {
                return ArticleStatusType.valueOf(source);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("변환 실패");
            }
        };
    }

}
