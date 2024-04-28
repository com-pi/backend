package com.example.boardservice.application.port.in;

import com.example.boardservice.domain.BuyAndSell;

import java.util.Optional;

public interface FindArticleQuery {

    Optional<BuyAndSell> findBuyAndSellById(Long id);

}
