package com.example.myplant.application.port.out;

import com.example.myplant.domain.Diary;

import java.util.List;
import java.util.Optional;

public interface LoadDiaryPort {
    Optional<Diary> loadDiaryById(Long id);
    List<Diary> loadAllDiaries();
}
