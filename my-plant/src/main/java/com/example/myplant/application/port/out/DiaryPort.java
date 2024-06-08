package com.example.myplant.application.port.out;

import com.example.myplant.domain.Diary;

import java.util.List;
import java.util.Optional;

public interface DiaryPort {
    Diary saveDiary(Diary diary);
    Optional<Diary> findById(Long Id);
    void deleteById(Long id);
    List<Diary> loadAllDiaries();
}
