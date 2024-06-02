package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.domain.BuyAndSell;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyAndSellRepository extends JpaRepository<BuyAndSell, Long> {
    Page<BuyAndSell> findByDeletionYn(String n, Pageable pageable);
}
