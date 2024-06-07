package com.example.boardservice.adapter.in.web.response;

import com.example.boardservice.domain.BuyAndSell;

import java.util.List;

public record BuyAndSellResponse(
        Long id,
        String title,
        List<String> imageUrls,
        Integer price
) {
    public static BuyAndSellResponse from(BuyAndSell buyAndSell) {
        return new BuyAndSellResponse(
                buyAndSell.getArticleId(),
                buyAndSell.getTitle(),
                buyAndSell.getImageUrls(),
                buyAndSell.getPrice()
        );
    }
}
