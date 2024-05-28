package com.example.myplant.application.port.out;

import com.example.myplant.domain.Diary;

public interface SaveDiaryPort {
    Diary saveDiary(Diary diary);
}
