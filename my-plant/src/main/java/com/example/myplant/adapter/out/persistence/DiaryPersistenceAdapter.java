package com.example.myplant.adapter.out.persistence;

import com.example.myplant.adapter.out.repository.DiaryRepository;
import com.example.myplant.application.port.out.DiaryPort;
import com.example.myplant.domain.Diary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DiaryPersistenceAdapter implements DiaryPort {

    private final DiaryRepository diaryRepository;

    public DiaryPersistenceAdapter(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    @Override
    public Diary saveDiary(Diary diary) {
        return diaryRepository.save(diary);
    }

    @Override
    public Optional<Diary> findById(Long id) {
        return diaryRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        diaryRepository.deleteById(id);
    }

    @Override
    public List<Diary> loadAllDiaries() {
        return diaryRepository.findAll();
    }

}
