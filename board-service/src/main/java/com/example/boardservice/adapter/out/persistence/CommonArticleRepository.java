package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.CommonArticleEntity;
import com.example.boardservice.adapter.out.persistence.entity.GeneralArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonArticleRepository extends JpaRepository<CommonArticleEntity, Long> {
    Page<CommonArticleEntity> findByDeletionYn(String deleteionYn, Pageable pageable);
}
