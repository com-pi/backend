package com.example.imagemodule;

import lombok.Getter;

@Getter
public enum MinioBucket {

    ARTICLE_PLANT_LIFE("plant-life"),
    ARTICLE_TRADE("article-trade"),
    TEMPORAL("temp");

    private final String bucketName;

    MinioBucket(String bucketName) {
        this.bucketName = bucketName;
    }
}
