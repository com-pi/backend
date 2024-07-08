package com.example.boardservice.adapter.out.persistence;

import com.example.boardservice.adapter.out.persistence.entity.DiaryArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryArticleRepository extends JpaRepository<DiaryArticleEntity, Long> {
}
