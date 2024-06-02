package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.BuyAndSell;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ArticleQueryPort {

    Page<BuyAndSell> getBuyAndSellList(int page);

    Optional<BuyAndSell> getBuyAndSell(Long id);
}
