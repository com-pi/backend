package com.example.boardservice.adapter.in.web.response;

import com.example.boardservice.adapter.out.persistence.converter.LocationToPointConverter;
import com.example.boardservice.domain.BuyAndSell;
import com.example.boardservice.domain.Location;

import java.util.List;

public record BuyAndSellDetailResponse(
        String title,
        List<String> imageUrls,
        Integer price,
        Long memberId,
        Location location,
        String content
) {
    public static BuyAndSellDetailResponse of(BuyAndSell buyAndSell) {
        return new BuyAndSellDetailResponse(
                buyAndSell.getTitle(),
                buyAndSell.getImageUrls(),
                buyAndSell.getPrice(),
                buyAndSell.getMember().getMemberId(),
                new LocationToPointConverter().convertToEntityAttribute(buyAndSell.getLocation()),
                buyAndSell.getContent()
        );
    }
}
