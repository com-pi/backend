package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.BuyAndSell;

public interface PostArticlePort {

    void postBuyAndSell(BuyAndSell buyAndSell);

}
