package com.example.boardservice.adapter.in.web.response;

import java.util.List;

public record BuyAndSellListResponse(
        List<BuyAndSellResponse> buyAndSellList
) {
    public static BuyAndSellListResponse of(List<BuyAndSellResponse> buyAndSellList) {
        return new BuyAndSellListResponse(buyAndSellList);
    }
}
