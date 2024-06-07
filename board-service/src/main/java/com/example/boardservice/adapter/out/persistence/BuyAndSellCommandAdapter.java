package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.BuyAndSellEntity;
import com.example.boardservice.application.port.out.BuyAndSellCommandPort;
import com.example.boardservice.domain.BuyAndSell;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BuyAndSellCommandAdapter implements BuyAndSellCommandPort {

    private final BuyAndSellRepository buyAndSellRepository;

    @Override
    public BuyAndSell save(BuyAndSell buyAndSell) {
        return buyAndSellRepository.save(BuyAndSellEntity.from(buyAndSell)).toDomain();
    }

    @Override
    public void delete(BuyAndSell buyAndSell) {
        BuyAndSellEntity entity = BuyAndSellEntity.from(buyAndSell);
        entity.delete();
        buyAndSellRepository.save(entity);
    }

}
