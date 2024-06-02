package com.example.boardservice.adapter.in.web.response;

import com.example.boardservice.domain.BuyAndSell;
import org.locationtech.jts.geom.Point;

import java.util.List;

public record BuyAndSellDetailResponse(
        String title,
        List<String> imageUrls,
        Integer price,
        Long memberId,
        Point location,
        String content
) {
    public static BuyAndSellDetailResponse of(BuyAndSell buyAndSell) {
        return new BuyAndSellDetailResponse(
                buyAndSell.getTitle(),
                buyAndSell.getImageUrls(),
                buyAndSell.getPrice(),
                buyAndSell.getMember().getMemberId(),
                buyAndSell.getLocation(),
                buyAndSell.getContent()
        );
    }
}
