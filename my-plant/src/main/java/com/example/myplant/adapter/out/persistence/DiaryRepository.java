package com.example.myplant.adapter.out.persistence;

import com.example.myplant.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
}
