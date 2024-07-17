package com.example.boardservice.domain;

import com.example.imagemodule.domain.MinioBucket;
import lombok.Getter;

@Getter
public enum ArticleType {

    COMMON_ARTICLE(null),
    GENERAL_BOARD(MinioBucket.ARTICLE_PLANT_LIFE),
    QNA_BOARD(MinioBucket.ARTICLE_PLANT_LIFE),
    DIARY_BOARD(MinioBucket.ARTICLE_PLANT_LIFE)
    ;

    private final MinioBucket bucket;

    ArticleType(MinioBucket bucket) {
        this.bucket = bucket;
    }

    public static ArticleType from(String type) {
        return switch (type.toLowerCase()) {
            case "common" -> COMMON_ARTICLE;
            case "general_board", "general" -> GENERAL_BOARD;
            case "qna_board", "qna" -> QNA_BOARD;
            case "diary_board", "diary" -> DIARY_BOARD;
            default -> throw new RuntimeException("존재하지 않는 게시글 타입입니다.");
        };
    }

}
