package com.example.boardservice.domain;

import com.example.imagemodule.MinioBucket;
import lombok.Getter;

@Getter
public enum ArticleType {

    BUY_AND_SELL(MinioBucket.ARTICLE_TRADE),
    PLANT_SITTER(MinioBucket.ARTICLE_TRADE),
    SHOW_OFF(MinioBucket.ARTICLE_PLANT_LIFE),
    QNA(MinioBucket.ARTICLE_PLANT_LIFE);

    private final MinioBucket bucket;

    ArticleType(MinioBucket bucket) {
        this.bucket = bucket;
    }

    public static ArticleType fromString(String type) {
        return switch (type.toLowerCase()) {
            case "buyandsell" -> BUY_AND_SELL;
            case "plantsitter" -> PLANT_SITTER;
            case "showoff" -> SHOW_OFF;
            case "qna" -> QNA;
            default -> throw new RuntimeException("enum 타입 변환 에러");
        };
    }

}
