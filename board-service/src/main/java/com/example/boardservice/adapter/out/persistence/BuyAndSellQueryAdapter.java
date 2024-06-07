package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.BuyAndSellEntity;
import com.example.boardservice.application.port.out.BuyAndSellQueryPort;
import com.example.boardservice.domain.BuyAndSell;
import com.example.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BuyAndSellQueryAdapter implements BuyAndSellQueryPort {

    private final BuyAndSellRepository buyAndSellRepository;

    @Override
    public Page<BuyAndSell> getBuyAndSellList(final int page) {
        final Page<BuyAndSellEntity> buyAndSellEntities = buyAndSellRepository.findByDeletionYn("N", PageRequest.of(page, 10));
        return buyAndSellEntities.map(BuyAndSellEntity::toDomain);
    }

    @Override
    public BuyAndSell getBuyAndSell(final Long articleId) {
        final BuyAndSellEntity buyAndSellEntity = buyAndSellRepository.findByArticleIdAndDeletionYn(articleId, "N")
                .orElseThrow(() -> new NotFoundException(BuyAndSellEntity.class));
        return buyAndSellEntity.toDomain();
    }

    @Override
    public Page<BuyAndSell> getBuyAndSellListByMemberId(Long memberId, Pageable pageable) {
        final Page<BuyAndSellEntity> buyAndSellEntities = buyAndSellRepository.findByMember_MemberIdAndDeletionYn(memberId, "N", pageable);
        return buyAndSellEntities.map(BuyAndSellEntity::toDomain);
    }
}
