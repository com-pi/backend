package com.example.boardservice.application.port.in;

import com.example.boardservice.adapter.in.web.response.BuyAndSellDetailResponse;
import com.example.boardservice.adapter.in.web.response.BuyAndSellListResponse;

public interface ReadArticleUseCase {

    BuyAndSellListResponse getBuyAndSellList(int page);

    BuyAndSellDetailResponse getBuyAndSell(Long id);
}
