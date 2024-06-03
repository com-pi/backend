package com.example.myplant.adapter.out.persistence;

import com.example.myplant.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByIsPublic(boolean isPublic);
}
