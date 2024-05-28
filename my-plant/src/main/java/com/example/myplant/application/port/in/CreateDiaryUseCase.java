package com.example.myplant.application.port.in;

import com.example.myplant.domain.Diary;

public interface CreateDiaryUseCase {
    Diary createDiary(Diary diary);
}
