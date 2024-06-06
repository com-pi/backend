package com.example.authserver.adapter.out.command;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRecordJpaRepository extends JpaRepository<EventRecordEntity, Long> {

    Optional<EventRecordEntity> findByTransactionId(String transactionId);

}
