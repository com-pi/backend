package com.example.imagemodule.domain;

import lombok.Builder;

import java.util.List;

@Builder
public record ImageAndThumbnail(
        String imageUrl,
        String thumbnailUrl
) {
    public static ImageAndThumbnail of(List<String> imageUrls) {
        return new ImageAndThumbnail(imageUrls.get(0), imageUrls.get(1));
    }
}
