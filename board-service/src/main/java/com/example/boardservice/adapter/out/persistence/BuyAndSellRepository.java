package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.BuyAndSellEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuyAndSellRepository extends JpaRepository<BuyAndSellEntity, Long> {
    Page<BuyAndSellEntity> findByDeletionYn(String deletionYn, Pageable pageable);

    Page<BuyAndSellEntity> findByMember_MemberIdAndDeletionYn(Long memberId, String deletionYn, Pageable pageable);

    Optional<BuyAndSellEntity> findByArticleIdAndDeletionYn(Long articleId, String n);
}
