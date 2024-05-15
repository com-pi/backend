package com.example.imagemodule.domain;

import lombok.Getter;

@Getter
public enum MinioBucket {

    ARTICLE_PLANT_LIFE("plant-life"),
    ARTICLE_TRADE("article-trade"),
    PROFILE_IMAGE("profile-image"),
    TEMPORAL("temp");

    private final String bucketName;

    MinioBucket(String bucketName) {
        this.bucketName = bucketName;
    }
}
