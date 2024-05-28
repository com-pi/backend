package com.example.myplant.application.port.in;

import com.example.myplant.domain.Diary;

public interface UpdateDiaryUseCase {
    Diary updateDiary(Long id, Diary diary);
}
