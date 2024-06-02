package com.example.boardservice.adapter.out.persistence.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;

@Converter
@RequiredArgsConstructor
public class JsonToStringListConverter implements AttributeConverter<List<String>, String> {
    
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        return objectMapper.writeValueAsString(strings);
    }

    @SneakyThrows
    @Override
    public List<String> convertToEntityAttribute(String jsonString) {
        return objectMapper.readValue(jsonString, new TypeReference<>() {
        });
    }

}
