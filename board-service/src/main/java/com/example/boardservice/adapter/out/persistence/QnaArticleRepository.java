package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.QnaArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QnaArticleRepository extends JpaRepository<QnaArticleEntity, Long> {

    Optional<QnaArticleEntity> findByArticleIdAndDeletionYn(Long articleId, String deletionYn);

    Page<QnaArticleEntity> findByDeletionYn(String deletionYn, Pageable pageable);

}
