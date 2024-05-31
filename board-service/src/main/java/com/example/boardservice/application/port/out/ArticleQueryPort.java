package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.BuyAndSell;
import org.springframework.data.domain.Page;

public interface ArticleQueryPort {

    Page<BuyAndSell> getBuyAndSellList(int page);

}
