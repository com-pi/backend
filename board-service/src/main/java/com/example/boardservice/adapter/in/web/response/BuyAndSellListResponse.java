package com.example.boardservice.adapter.in.web.response;

import com.example.boardservice.domain.BuyAndSell;
import org.springframework.data.domain.Page;

import java.util.List;

public record BuyAndSellListResponse(
        List<BuyAndSellResponse> buyAndSellList
) {

    public static BuyAndSellListResponse from(Page<BuyAndSell> buyAndSellPage) {
        List<BuyAndSellResponse> response = buyAndSellPage.stream()
                .map(BuyAndSellResponse::from)
                .toList();
        return new BuyAndSellListResponse(response);
    }
}
