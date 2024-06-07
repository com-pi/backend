package com.example.boardservice.application.port.out;

import com.example.boardservice.domain.BuyAndSell;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BuyAndSellQueryPort {

    Page<BuyAndSell> getBuyAndSellList(int page);

    BuyAndSell getBuyAndSell(Long id);

    Page<BuyAndSell> getBuyAndSellListByMemberId(Long memberId, Pageable pageable);
}
