package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.GeneralArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GeneralArticleRepository extends JpaRepository<GeneralArticleEntity, Long> {

    Optional<GeneralArticleEntity> findByArticleIdAndDeletionYn(Long articleId, String deletionYn);

    Page<GeneralArticleEntity> findByDeletionYn(String deletionYn, Pageable pageable);
}
