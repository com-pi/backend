package com.example.boardservice.application.port.in;

import com.example.boardservice.domain.Area;
import com.example.boardservice.domain.ArticleType;
import com.example.boardservice.domain.BuyAndSell;
import com.example.boardservice.domain.User;

import java.util.List;

public record BuyAndSellResponse(
        Long id,
        User user,
        ArticleType articleType,
        String title,
        String content,
        Integer price,
        Area area,
        List<String> imageUrls,
        List<String> hashtags
) {
    public static BuyAndSellResponse fromEntity(BuyAndSell buyAndSell) {
        return new BuyAndSellResponse(
                buyAndSell.getId(),
                buyAndSell.getUser(),
                buyAndSell.getArticleType(),
                buyAndSell.getTitle(),
                buyAndSell.getContent(),
                buyAndSell.getPrice(),
                buyAndSell.getArea(),
                buyAndSell.getImageUrls(),
                buyAndSell.getHashtags()
        );
    }
}
