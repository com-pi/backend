package com.example.myplant.application.port.in;

import com.example.myplant.adapter.in.web.command.DiaryCommand;
import com.example.myplant.adapter.in.web.command.UpdateDiaryCommand;
import com.example.myplant.domain.Diary;

import java.util.List;

public interface DiaryUseCase {
    Long createDiary(DiaryCommand command);
    void updateDiary(UpdateDiaryCommand command);
    void deleteDiary(Long id);
    List<Diary> getAllDiaries();
}
