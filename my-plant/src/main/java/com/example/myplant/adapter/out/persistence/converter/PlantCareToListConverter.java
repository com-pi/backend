package com.example.myplant.adapter.out.persistence.converter;

import com.example.myplant.domain.PlantCare;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;

@Converter
@RequiredArgsConstructor
public class PlantCareToListConverter implements AttributeConverter<List<PlantCare>, String> {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(List<PlantCare> plantCareList) {
        return objectMapper.writeValueAsString(plantCareList);
    }

    @SneakyThrows
    @Override
    public List<PlantCare> convertToEntityAttribute(String string) {
       return objectMapper.readValue(string, new TypeReference<>() {});
   }
}
