package com.example.imagemodule.application.port;

import com.example.imagemodule.domain.MinioBucket;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record SaveImagesCommand(
        List<MultipartFile> imageFiles,
        MinioBucket bucket
) {
    public static SaveImagesCommand of(
            List<MultipartFile> imageFiles,
            MinioBucket minioBucket) {
        return new SaveImagesCommand(imageFiles, minioBucket);
    }
}
