package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.BuyAndSell;

public interface UpdateArticlePort {
    void updateBuyAndSell(BuyAndSell buyAndSell);
}
