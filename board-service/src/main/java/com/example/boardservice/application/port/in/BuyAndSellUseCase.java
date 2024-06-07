package com.example.boardservice.application.port.in;

import com.example.boardservice.domain.BuyAndSell;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BuyAndSellUseCase {

    Long postBuyAndSell(BuyAndSell buyAndSell, List<MultipartFile> imageFiles);

    Long updateBuyAndSell(BuyAndSell buyAndSell, List<MultipartFile> imageFiles);

    Long delete(BuyAndSell buyAndSell);

    Page<BuyAndSell> getBuyAndSellList(int page);

    BuyAndSell getBuyAndSell(Long id);

    Page<BuyAndSell> getBuyAndSellListByMemberId(Long memberId, Pageable pageable);
}

