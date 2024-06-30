package com.example.boardservice.domain;

import com.example.imagemodule.domain.MinioBucket;
import lombok.Getter;

@Getter
public enum ArticleType {

    GENERAL_BOARD(MinioBucket.ARTICLE_PLANT_LIFE),
    QNA(MinioBucket.ARTICLE_PLANT_LIFE);

    private final MinioBucket bucket;

    ArticleType(MinioBucket bucket) {
        this.bucket = bucket;
    }

    public static ArticleType fromString(String type) {
        return switch (type.toLowerCase()) {
            case "general_board" -> GENERAL_BOARD;
            case "qna" -> QNA;
            default -> throw new RuntimeException("enum 타입 변환 에러");
        };
    }

}
