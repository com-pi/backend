package com.example.myplant.application.service;

import com.example.myplant.domain.Diary;
import com.example.myplant.application.port.in.CreateDiaryUseCase;
import com.example.myplant.application.port.in.UpdateDiaryUseCase;
import com.example.myplant.application.port.out.LoadDiaryPort;
import com.example.myplant.application.port.out.SaveDiaryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiaryService implements CreateDiaryUseCase, UpdateDiaryUseCase {
    private final SaveDiaryPort saveDiaryPort;
    private final LoadDiaryPort loadDiaryPort;

    @Autowired
    public DiaryService(SaveDiaryPort saveDiaryPort, LoadDiaryPort loadDiaryPort) {
        this.saveDiaryPort = saveDiaryPort;
        this.loadDiaryPort = loadDiaryPort;
    }

    @Override
    public Diary createDiary(Diary diary) {
        return saveDiaryPort.saveDiary(diary);
    }

    @Override
    public Diary updateDiary(Long id, Diary diary) {
        Diary existingDiary = loadDiaryPort.loadDiaryById(id)
                .orElseThrow(() -> new RuntimeException("Diary not found"));
        existingDiary.setTitle(diary.getTitle());
        existingDiary.setContent(diary.getContent());
        existingDiary.setImageUrl(diary.getImageUrl());
        return saveDiaryPort.saveDiary(existingDiary);
    }

    public List<Diary> getAllDiaries() {
        return loadDiaryPort.loadAllDiaries();
    }
}