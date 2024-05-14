package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.ArticleType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record SaveImagesCommand(
        List<MultipartFile> imageFiles,
        ArticleType articleType
) {
    public static SaveImagesCommand of(
            List<MultipartFile> imageFiles,
            ArticleType articleType) {
        return new SaveImagesCommand(imageFiles, articleType);
    }
}
