package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.BuyAndSell;

public interface BuyAndSellCommandPort {

    BuyAndSell save(BuyAndSell buyAndSell);

    void delete(BuyAndSell buyAndSell);
}
