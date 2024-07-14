package com.example.authserver.adapter.out.repository;

import com.example.authserver.adapter.out.entity.EventRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRecordJpaRepository extends JpaRepository<EventRecordEntity, Long> {

    Optional<EventRecordEntity> findByTransactionId(String transactionId);

}
